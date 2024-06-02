package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.filter.UserFilter;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车
 */
@Tag(name = "购物车")
@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Operation(summary = "商品列表")
    @PostMapping("list")
    public CommonResponse list() {
        List<CartVO> cartList = cartService.list(UserFilter.curUser.getId());
        return CommonResponse.success(cartList);
    }

    @Operation(summary = "添加商品到购物车")
    @PostMapping("add")
    public CommonResponse add(Integer productId, Integer count) {
        List<CartVO> cartList =  cartService.add(UserFilter.curUser.getId(), productId, count);

        return CommonResponse.success(cartList);
    }

    @Operation(summary = "更新购物车")
    @PostMapping("update")
    public CommonResponse update(Integer productId, Integer count) {
        List<CartVO> cartList =  cartService.update(UserFilter.curUser.getId(), productId, count);

        return CommonResponse.success(cartList);
    }

    @Operation(summary = "删除购物车")
    @PostMapping("delete")
    public CommonResponse delete(Integer productId) {
        List<CartVO> cartList =  cartService.delete(UserFilter.curUser.getId(), productId);
        return CommonResponse.success(cartList);
    }

    @Operation(summary = "选中购物车")
    @PostMapping("select")
    public CommonResponse select(Integer productId) {
        List<CartVO> cartList =  cartService.delete(UserFilter.curUser.getId(), productId);
        return CommonResponse.success(cartList);
    }

    @Operation(summary = "全选购物车")
    @PostMapping("select-all")
    public CommonResponse selectAll(Integer productId) {
        List<CartVO> cartList =  cartService.delete(UserFilter.curUser.getId(), productId);
        return CommonResponse.success(cartList);
    }
}
