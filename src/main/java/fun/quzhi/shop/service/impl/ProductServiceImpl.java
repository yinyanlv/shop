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
}
