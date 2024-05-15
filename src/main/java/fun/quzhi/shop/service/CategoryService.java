package fun.quzhi.shop.service;

import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.model.request.AddCategoryReq;

/**
 * 分类目录
 */
public interface CategoryService {
    public void add(AddCategoryReq addCategoryReq) throws ShopException;
}
