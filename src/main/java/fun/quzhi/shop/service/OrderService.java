package fun.quzhi.shop.service;

import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.model.vo.OrderVO;

import java.util.List;

/**
 * 订单
 */
public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderCode);

    void cancel(String orderCode);

    String qrcode(String orderCode);
}
