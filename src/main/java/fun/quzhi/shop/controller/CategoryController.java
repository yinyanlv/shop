package fun.quzhi.shop.controller;

import com.github.pagehelper.PageInfo;
import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.model.request.AddCategoryReq;
import fun.quzhi.shop.model.vo.CategoryVO;
import fun.quzhi.shop.service.CategoryService;
import fun.quzhi.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 目录
 */
@Tag(name = "商品目录")
@Controller
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Operation(summary = "添加商品目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public CommonResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        if (addCategoryReq.getName() == null) {
            return CommonResponse.error(ShopExceptionEnum.PARAM_IS_NULL);
        }

        User curUser = (User)session.getAttribute(Constant.SESSION_USER_KEY);
        if (curUser == null) {
            return CommonResponse.error(ShopExceptionEnum.NEED_LOGIN);
        }

        boolean isAdmin = userService.isAdmin(curUser);
        if (!isAdmin)  {
            return CommonResponse.error(ShopExceptionEnum.NEED_ADMIN);
        }

        addCategoryReq.setCreateBy(curUser.getId());
        addCategoryReq.setUpdateBy(curUser.getId());

        categoryService.add(addCategoryReq);

        return CommonResponse.success();
    }

    @Operation(summary = "删除商品目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public CommonResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return CommonResponse.success();
    }


    @Operation(summary = "后台查询商品目录")
    @PostMapping("admin/category/list")
    @ResponseBody
    public CommonResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return CommonResponse.success(pageInfo);
    }

    @Operation(summary = "前台查询商品目录")
    @PostMapping("category/list")
    @ResponseBody
    public CommonResponse listCategoryForCustomer() {
        List<CategoryVO> list = categoryService.listForCustomer(0);
        return CommonResponse.success(list);
    }
}
