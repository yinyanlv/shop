package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.filter.UserFilter;
import fun.quzhi.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车
 */
@Tag(name = "购物车")
@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Operation(summary = "添加商品到购物车")
    @PostMapping("add")
    public CommonResponse add(Integer productId, Integer count) {
        cartService.add(UserFilter.curUser.getId(), productId, count);
        return CommonResponse.success();
    }
}
