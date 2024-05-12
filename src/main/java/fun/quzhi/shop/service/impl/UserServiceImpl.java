package fun.quzhi.shop.service.impl;

import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.dao.UserMapper;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.UserService;
import fun.quzhi.shop.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * 用户
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${app.salt}")
    private String appSalt;

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

        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        try {
            user.setPassword(MD5Utils.getPasswordMD5Str(password, appSalt));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setSalt(appSalt);

        // TODO
        String createBy = "40e17455-7ae0-4d3b-859d-08e4b2794153";
        user.setCreateBy(createBy);
        user.setUpdateBy(createBy);

        int count = userMapper.insertSelective(user);
        if (count == 0) {
           throw new ShopException(ShopExceptionEnum.REGISTER_ERROR);
        }
    }

    public static void main(String[] args) {
        var uuid = UUID.randomUUID().toString();

        System.out.println(uuid);
    }
}
