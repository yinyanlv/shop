package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.ProductListReq;
import fun.quzhi.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "前台商品")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Operation(summary = "商品详情")
    @PostMapping("product/detail")
    public CommonResponse detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return CommonResponse.success(product);
    }

    @Operation(summary = "商品查找")
    @PostMapping("product/list")
    public CommonResponse list(@Valid @RequestBody ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return CommonResponse.success(list);
    }
}
