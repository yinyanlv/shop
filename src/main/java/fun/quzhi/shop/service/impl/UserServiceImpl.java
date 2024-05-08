package fun.quzhi.shop.service.impl;


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


}
