package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("order/create")
    public CommonResponse create(@RequestBody CreateOrderReq createOrderReq) {
        return null;
    }
}
