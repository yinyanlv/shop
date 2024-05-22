package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.request.AddProductReq;
import fun.quzhi.shop.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "后台商品管理")
@Controller
public class ProductAdminController {

    @Autowired
    ProductService productService;

    @PostMapping("admin/product/add")
    @ResponseBody
    public CommonResponse AddProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return CommonResponse.success();
    }
}

