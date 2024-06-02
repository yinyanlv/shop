package fun.quzhi.shop.service.impl;

import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.CartMapper;
import fun.quzhi.shop.model.dao.ProductMapper;
import fun.quzhi.shop.model.pojo.Cart;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(String userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(String userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品之前不在购物车里
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            // TODO
            String createBy = "40e17455-7ae0-4d3b-859d-08e4b2794153";
            cart.setCreateBy(createBy);
            cart.setUpdateBy(createBy);
            cartMapper.insertSelective(cart);
        } else {
            // 更新数量
            count = cart.getQuantity() + count;
            Cart newCart = new Cart();
            newCart.setId(cart.getId());
            newCart.setQuantity(count);
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(newCart);
        }
        return list(userId);
    }

    private void  validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        // 判断商品是否存在，商品是否上架
        if (product == null || product.getId().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_SALE);
        }
        // 判断商品库存
        if (count > product.getStock()) {
            throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(String userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new ShopException(ShopExceptionEnum.UPDATE_FAILED);
        } else {
            Cart newCart = new Cart();
            newCart.setId(cart.getId());
            newCart.setQuantity(count);
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(newCart);
        }
        return list(userId);
    }

    @Override
    public List<CartVO> delete(String userId, Integer productId) {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new ShopException(ShopExceptionEnum.DELETE_FAILED);
        } else {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return list(userId);
    }
}
