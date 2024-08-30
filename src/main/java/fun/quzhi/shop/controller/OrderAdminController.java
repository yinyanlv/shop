package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.vo.OrderStatisticsVO;
import fun.quzhi.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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

    @Operation(summary = "管理员发货")
    @PostMapping("admin/order/delivery")
    public CommonResponse delivery(@RequestParam String orderCode) {
        orderService.delivery(orderCode);
        return CommonResponse.success();
    }

    @Operation(summary = "管理员订单完成")
    @PostMapping("order/finish")
    public CommonResponse finish(@RequestParam String orderCode) {
        orderService.finish(orderCode);
        return CommonResponse.success();
    }

    @Operation(summary = "每日订单量统计")
    @PostMapping("admin/order/statistics")
    public CommonResponse orderStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        List<OrderStatisticsVO> list = orderService.statistics(startDate, endDate);
        return CommonResponse.success(list);
    }
}
