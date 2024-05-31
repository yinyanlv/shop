package fun.quzhi.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.ProductMapper;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.query.ProductListQuery;
import fun.quzhi.shop.model.request.AddProductReq;
import fun.quzhi.shop.model.request.ProductListReq;
import fun.quzhi.shop.model.vo.CategoryVO;
import fun.quzhi.shop.service.CategoryService;
import fun.quzhi.shop.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

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


    @Override
    public void batchUpdateStatus(Integer[] ids, Integer status) {
        // TODO
        String updateBy = "40e17455-7ae0-4d3b-859d-08e4b2794153";
        productMapper.batchUpdateStatusByPrimaryKey(ids, status, updateBy);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products =  productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);

        return pageInfo;
    }

    @Override
    public Product detail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo list(ProductListReq productListReq) {
        ProductListQuery productListQuery = new ProductListQuery();

        // 关键字
        if (!StringUtils.isEmpty(productListReq.getName())) {
            String keyword = new StringBuffer().append("%").append(productListReq.getName()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }

        // 目录
        if (productListReq.getCategoryId() != null) {
            List<CategoryVO> list = categoryService.listForCustomer(productListReq.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(list, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        // 排序
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        } else {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }

        List<Product> productList = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo<>(productList);
        return  pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null) {
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildren(), categoryIds);
            }
        }
    }
}
