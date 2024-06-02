package fun.quzhi.shop.service;

import fun.quzhi.shop.model.vo.CartVO;

import java.util.List;

/**
 * 购物车
 */
public interface CartService {

    List<CartVO> list(String userId);

    List<CartVO> add(String userId, Integer productId, Integer count);

    List<CartVO> update(String userId, Integer productId, Integer count);

    List<CartVO> delete(String userId, Integer productId);

    List<CartVO> selectOrNot(String userId, Integer productId, Integer selected);

    List<CartVO> selectAllOrNot(String userId, Integer selected);
}
