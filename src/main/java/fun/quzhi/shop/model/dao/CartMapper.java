package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Cart;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart row);

    int insertSelective(Cart row);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart row);

    int updateByPrimaryKey(Cart row);

    Cart selectByUserIdAndProductId(@Param("userId") String userId, @Param("productId") Integer productId);
}