package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.UserMapper;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.EmailService;
import fun.quzhi.shop.service.UserService;
import fun.quzhi.shop.util.EmailUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
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
@Tag(name = "用户")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

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

    @PostMapping("/login")
    @ResponseBody
    public CommonResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws ShopException {
        if (StringUtils.isEmpty(username)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_USERNAME);
        }
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        user.setPassword(null);
        user.setSalt(null);
        session.setAttribute(Constant.SESSION_USER_KEY, user);
        return  CommonResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public CommonResponse updateUserInfo(HttpSession session, @RequestParam String nickname) throws ShopException {
        User curUser = (User)session.getAttribute(Constant.SESSION_USER_KEY);
        if (curUser == null) {
            return CommonResponse.error(ShopExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(curUser.getId());
        user.setNickname(nickname);
        userService.updateUserInfo(user);
        return CommonResponse.success();
    }

    @GetMapping("/logout")
    @ResponseBody
    public CommonResponse logout(HttpSession session) {
       session.removeAttribute(Constant.SESSION_USER_KEY);
       return CommonResponse.success();
    }

    @PostMapping("/admin/login")
    @ResponseBody
    public CommonResponse adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws ShopException {
        if (StringUtils.isEmpty(username)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_USERNAME);
        }
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.error(ShopExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        if (userService.isAdmin(user))  {
            user.setPassword(null);
            user.setSalt(null);
            session.setAttribute(Constant.SESSION_USER_KEY, user);
            return  CommonResponse.success(user);
        } else {
            return  CommonResponse.error(ShopExceptionEnum.NEED_ADMIN);
        }
    }

    @PostMapping("/user/sendEmail")
    @ResponseBody
    public CommonResponse sendEmail(@RequestParam("email") String email) throws ShopException {
        boolean isValid = EmailUtil.isValidEmail(email);
        if (!isValid) {
            return CommonResponse.error(ShopExceptionEnum.INVALID_EMAIL);
        }
        boolean isRegistered = userService.checkEmailRegistered(email);
        if (isRegistered) {
            return CommonResponse.error(ShopExceptionEnum.EMAIL_REGISTERED);
        }
        String verifyCode = EmailUtil.genVerifyCode();
        boolean isSaved = emailService.saveEmailToRedis(email, verifyCode);
        if (isSaved) {
            emailService.sendSimpleMessage(email, "发送验证码", "欢迎注册，您的验证码是：" + verifyCode);
            return CommonResponse.success();
        } else {
            return CommonResponse.error(ShopExceptionEnum.EMAIL_ALREADY_SEND);
        }
    }
}

