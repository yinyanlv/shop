package fun.quzhi.shop.service.impl;


import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.UserMapper;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用户
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User getUser(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String username, String password) throws ShopException {
        // 查询用户名是否已存在
        User res = userMapper.selectByUsername(username);
        if (res != null) {
            throw new ShopException(ShopExceptionEnum.USERNAME_EXISTS);
        }

        User user = new User();
        user.setId("test");
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateBy("x");
        user.setUpdateBy("x");

        int count = userMapper.insertSelective(user);
        if (count == 0) {
           throw new ShopException(ShopExceptionEnum.REGISTER_ERROR);
        }
    }

}
