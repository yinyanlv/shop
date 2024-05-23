package fun.quzhi.shop.service.impl;

import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.ProductMapper;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.AddProductReq;
import fun.quzhi.shop.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product oldProduct = productMapper.selectByName(addProductReq.getName());

        if (oldProduct != null) {
            throw new ShopException(ShopExceptionEnum.NAME_EXISTS);
        }

        // TODO
        String createBy = "40e17455-7ae0-4d3b-859d-08e4b2794153";
        product.setCreateBy(createBy);
        product.setUpdateBy(createBy);

        int count = productMapper.insertSelective(product);

        if (count == 0) {
            throw new ShopException(ShopExceptionEnum.CRATE_FAILED);
        }
    }

    @Override
    public void update(Product product) {
        Product oldProduct = productMapper.selectByName(product.getName());

        // 该商品名称已被使用
        if (oldProduct != null && oldProduct.getId().equals(product.getId())) {
            throw new ShopException(ShopExceptionEnum.NAME_EXISTS);
        }

        // TODO
        String updateBy = "40e17455-7ae0-4d3b-859d-08e4b2794153";
        product.setUpdateBy(updateBy);

        int count = productMapper.updateByPrimaryKeySelective(product);

        if (count == 0) {
            throw new ShopException(ShopExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        Product oldProduct = productMapper.selectByPrimaryKey(id);
        // 商品不存在
        if (oldProduct == null)  {
            throw new ShopException(ShopExceptionEnum.DELETE_FAILED);
        }

        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ShopException(ShopExceptionEnum.DELETE_FAILED);
        }
    }
}
