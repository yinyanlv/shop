package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Product;
import org.apache.ibatis.annotations.Param;

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
}