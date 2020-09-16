package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    List<User> selectUsers(@Param("userName") String userName, @Param("offset") int offset, @Param("limit") int limit);

    int countUsers(String userName);

    void insertUsers(List<User> users);

    void updateUser(User user);

    void deleteUser(int id);

    User selectUser(int id);
}
