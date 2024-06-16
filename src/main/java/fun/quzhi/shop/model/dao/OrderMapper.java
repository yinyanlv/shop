package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order row);

    int insertSelective(Order row);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order row);

    int updateByPrimaryKey(Order row);

    Order selectByOrderCode(String orderCode);

    List<Order> selectForCustomer(String userId);

    List<Order> selectAllForAdmin();
}