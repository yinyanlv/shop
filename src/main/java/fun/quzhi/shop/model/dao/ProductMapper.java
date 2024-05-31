package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKeyWithBLOBs(Product row);

    int updateByPrimaryKey(Product row);

    Product selectByName(String name);

    int batchUpdateStatusByPrimaryKey(@Param("ids") Integer[] ids, @Param("status") Integer status, @Param("updateBy") String updateBy);

    List<Product> selectListForAdmin();

    List<Product> selectList(@Param("query") ProductListQuery query);
}