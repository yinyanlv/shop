package fun.quzhi.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.CategoryMapper;
import fun.quzhi.shop.model.pojo.Category;
import fun.quzhi.shop.model.request.AddCategoryReq;
import fun.quzhi.shop.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category oldCategory = categoryMapper.selectByName(addCategoryReq.getName());

        if (oldCategory != null) {
            throw new ShopException(ShopExceptionEnum.NAME_EXISTS);
        }

        int count = categoryMapper.insertSelective(category);

        if (count == 0) {
            throw new ShopException(ShopExceptionEnum.CRATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new ShopException(ShopExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0)  {
            throw new ShopException(ShopExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type,sort");
        List<Category> list = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
