package fun.quzhi.shop.model.dao;

import fun.quzhi.shop.model.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    User selectByUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    User selectByEmail(String email);
}