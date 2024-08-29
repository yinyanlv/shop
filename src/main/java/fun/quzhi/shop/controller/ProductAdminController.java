package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    @PostMapping("admin/product/batchUpdateStatus")
    @ResponseBody
    public CommonResponse batchUpdateStatus( @RequestParam Integer[] ids, Integer status) {
        productService.batchUpdateStatus(ids, status);
        return CommonResponse.success();
    }

    @Operation(summary = "后台商品列表")
    @PostMapping("admin/product/list")
    @ResponseBody
    public CommonResponse list( @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return CommonResponse.success(pageInfo);
    }

    @Operation(summary = "批量上传商品")
    @PostMapping("admin/importProduct")
    @ResponseBody
    public CommonResponse batchUploadProduct(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        UUID uuid =  UUID.randomUUID();
        String newFileName = uuid +  ext;
        File dir =  new File(Constant.FILE_UPLOAD_PATH);
        File destFile = new File(Constant.FILE_UPLOAD_PATH + newFileName);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new ShopException(ShopExceptionEnum.UPLOAD_DIR_ERROR);
            }
        }
        try {
            file.transferTo(destFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
        productService.addProductByExcel(destFile);
        return CommonResponse.success();
    }

    @Operation(summary = "批量更新商品")
    @PostMapping("admin/product/batchUpdate")
    @ResponseBody
    public CommonResponse batchUpdateProduct(@Valid @RequestBody List<UpdateProductReq> updateProductReqList) {
        for (int i = 0; i < updateProductReqList.size(); i++) {
           UpdateProductReq updateProductReq = updateProductReqList.get(i);
            Product product = new Product();
            BeanUtils.copyProperties(updateProductReq, product);
            productService.update(product);
        }
        return CommonResponse.success();
    }
}

