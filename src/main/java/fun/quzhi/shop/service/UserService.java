package fun.quzhi.shop.service;

import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.model.pojo.User;

/**
 * 用户
 */
public interface UserService {

    User getUser(String id);

    void register(String username, String password) throws ShopException;

    User login(String username, String password) throws ShopException;

    void updateUserInfo(User user) throws ShopException;

    boolean isAdmin(User user);
}
