package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.Cart;
import fun.quzhi.shop.model.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart row);

    int insertSelective(Cart row);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart row);

    int updateByPrimaryKey(Cart row);

    List<CartVO> selectList(@Param("userId") String userId);

    Cart selectByUserIdAndProductId(@Param("userId") String userId, @Param("productId") Integer productId);

    Integer selectOrNot(@Param("userId") String userId, @Param("productId") Integer productId, @Param("selected") Integer selected);
}