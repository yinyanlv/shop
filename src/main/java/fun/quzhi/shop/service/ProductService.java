package fun.quzhi.shop.service;

import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.AddProductReq;

/**
 * 商品
 */
public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(Product product);

    void delete(Integer id);
}
