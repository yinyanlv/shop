package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.OrderVO;
import fun.quzhi.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Operation(summary = "创建订单")
    @PostMapping("order/create")
    public CommonResponse create(@RequestBody CreateOrderReq createOrderReq) {
        String orderCode = orderService.create(createOrderReq);
        return CommonResponse.success(orderCode);
    }

    @Operation(summary = "前台订单详情")
    @PostMapping("order/detail")
    public CommonResponse detail(@RequestParam String orderCode) {
        OrderVO orderVO = orderService.detail(orderCode);
        return CommonResponse.success(orderVO);
    }
}
