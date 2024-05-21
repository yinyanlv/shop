package fun.quzhi.shop.service;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.model.request.AddCategoryReq;

/**
 * 分类目录
 */
public interface CategoryService {
    void add(AddCategoryReq addCategoryReq) throws ShopException;

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
