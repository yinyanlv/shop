package fun.quzhi.shop.service;

import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.model.pojo.User;

/**
 * 邮箱
 */
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    Boolean saveEmailToRedis(String email, String verifyCode);
}
