package fun.quzhi.shop.service;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.model.vo.OrderStatisticsVO;
import fun.quzhi.shop.model.vo.OrderVO;

import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    OrderVO detail(String orderCode);

    void cancel(String orderCode);

    String qrcode(String orderCode);

    void pay(String orderCode);

    void delivery(String orderCode);

    void finish(String orderCode);

    List<OrderStatisticsVO> statistics(Date startDate, Date endDate);
}
