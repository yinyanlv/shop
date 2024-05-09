package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    @ResponseBody
    public User getUserInfo(@RequestParam String id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws ShopException {
        if (StringUtils.isEmpty(username)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_USERNAME);
        }

        if (StringUtils.isEmpty(password)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_PASSWORD);
        }

        userService.register(username, password);

        return CommonResponse.success();
    }

}
