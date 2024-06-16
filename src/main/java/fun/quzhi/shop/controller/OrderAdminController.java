package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单后端管理
 */
@Tag(name = "后台订单管理")
@RestController
public class OrderAdminController {
    @Autowired
    OrderService orderService;

    @Operation(summary = "管理员订单列表")
    @PostMapping("admin/order/list")
    public CommonResponse listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return CommonResponse.success(pageInfo);
    }
}
