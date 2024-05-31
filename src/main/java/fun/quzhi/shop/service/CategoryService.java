package fun.quzhi.shop.service;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.model.request.AddCategoryReq;
import fun.quzhi.shop.model.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 分类目录
 */
public interface CategoryService {
    void add(AddCategoryReq addCategoryReq) throws ShopException;

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    @Cacheable(value = "categoryListForCustomer")
    List<CategoryVO> listForCustomer(Integer parentId);
}
