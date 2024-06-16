package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.OrderVO;
import fun.quzhi.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
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

    @Operation(summary = "前台订单列表")
    @PostMapping("order/list")
    public CommonResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForCustomer(pageNum, pageSize);
        return CommonResponse.success(pageInfo);
    }

    @Operation(summary = "前台订单取消")
    @PostMapping("order/cancel")
    public CommonResponse cancel(@RequestParam String orderCode) {
        orderService.cancel(orderCode);
        return CommonResponse.success();
    }

    @Operation(summary = "生成订单二维码")
    @PostMapping("order/qrcode")
    public CommonResponse qrcode(@RequestParam String orderCode) {
        String qrcodeUrl = orderService.qrcode(orderCode);
        return CommonResponse.success(qrcodeUrl);
    }

    @Operation(summary = "支付订单")
    @PostMapping("pay")
    public CommonResponse pay(@RequestParam String orderCode) {
        orderService.pay(orderCode);
        return CommonResponse.success();
    }
}
