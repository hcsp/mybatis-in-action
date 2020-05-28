package com.github.hcsp.mybatis.mapper;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select * from user")
    List<User> getUsers();
}
