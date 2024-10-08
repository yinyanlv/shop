package fun.quzhi.shop.service;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.AddProductReq;
import fun.quzhi.shop.model.request.ProductListReq;

import java.io.File;
import java.io.IOException;

/**
 * 商品
 */
public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(Product product);

    void delete(Integer id);

    void batchUpdateStatus(Integer[] ids, Integer status);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);

    void addProductByExcel(File file) throws IOException;
}
