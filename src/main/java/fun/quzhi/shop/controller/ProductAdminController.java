package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.AddProductReq;
import fun.quzhi.shop.model.request.UpdateProductReq;
import fun.quzhi.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "后台商品管理")
@Controller
public class ProductAdminController {

    @Autowired
    ProductService productService;

    @Operation(summary = "新增商品")
    @PostMapping("admin/product/add")
    @ResponseBody
    public CommonResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return CommonResponse.success();
    }

    @Operation(summary = "更新商品")
    @PostMapping("admin/product/update")
    @ResponseBody
    public CommonResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.update(product);
        return CommonResponse.success();
    }

    @Operation(summary = "删除商品")
    @PostMapping("admin/product/delete")
    @ResponseBody
    public CommonResponse deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return CommonResponse.success();
    }

    @Operation(summary = "批量更新商品状态")
    @PostMapping("admin/product/batch-update-status")
    @ResponseBody
    public CommonResponse batchUpdateStatus( @RequestParam Integer[] ids, Integer status) {
        productService.batchUpdateStatus(ids, status);
        return CommonResponse.success();
    }
}

