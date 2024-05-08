package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKeyWithBLOBs(Product row);

    int updateByPrimaryKey(Product row);
}