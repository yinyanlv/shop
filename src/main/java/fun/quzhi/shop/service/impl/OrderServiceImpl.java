package fun.quzhi.shop.service.impl;

import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.filter.UserFilter;
import fun.quzhi.shop.model.dao.CartMapper;
import fun.quzhi.shop.model.dao.ProductMapper;
import fun.quzhi.shop.model.pojo.Order;
import fun.quzhi.shop.model.pojo.OrderItem;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.service.CartService;
import fun.quzhi.shop.service.OrderService;
import fun.quzhi.shop.util.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;


    public String create(CreateOrderReq createOrderReq) {
        // 用户id
        String userId = UserFilter.curUser.getId();

        // 从购物车查找已勾选的商品
        List<CartVO> cartVOList = cartService.list(userId);
        List<CartVO> selectedList = new ArrayList<>();
        for (CartVO cartVO : cartVOList) {
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                selectedList.add(cartVO);
            }
        }
        cartVOList = selectedList;
        // 购物车已勾选的为空
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new ShopException(ShopExceptionEnum.CART_SELECTED_EMPTY);
        }
        // 判断商品是否存在、装填、库存
        validateStatusAndStock(cartVOList);
        // 把购物车对象转换成订单item对象
        List<OrderItem > orderItemList = toOrderItemList(cartVOList);
        // 库存校验，扣库存
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            Integer stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
        // 从购物车中删除已勾选的商品
        cleanCart(cartVOList);
        // 生成订单
        Order order = new Order();
        String orderCode = OrderCodeFactory.getOrderCode();
        order.setCode(orderCode);
        order.setUserId(userId);
        order.setCreateBy(userId);
        order.setUpdateBy(userId);

        // 循环保存商品到order item表

        return null;
    }

    public void validateStatusAndStock(List<CartVO> cartVOList) {
        for (CartVO cartVO : cartVOList) {
            Integer productId = cartVO.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            // 判断商品是否存在，商品是否上架
            if (product == null || product.getId().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_SALE);
            }
            Integer count = cartVO.getQuantity();
            // 判断商品库存
            if (count > product.getStock()) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_ENOUGH);
            }
        }
    }

    public List<OrderItem> toOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartVO cartVO : cartVOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImage(cartVO.getProductImage());
            orderItem.setProductPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    public void cleanCart(List<CartVO> cartVOList) {
        for(CartVO cartVO : cartVOList) {
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }
}
