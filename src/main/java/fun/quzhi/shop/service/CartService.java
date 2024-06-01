package fun.quzhi.shop.service;

import fun.quzhi.shop.model.vo.CartVO;

import java.util.List;

/**
 * 购物车
 */
public interface CartService {

    List<CartVO> add(String userId, Integer productId, Integer count);
}
