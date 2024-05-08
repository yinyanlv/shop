package fun.quzhi.shop.controller;

import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

}
