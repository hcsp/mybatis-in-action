package com.github.hcsp.mybatis.mapper;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    List<User> getUserByPage(@Param("username") String username, @Param("pageSize") int pageSize, @Param("pageNum") int pageNum);

    Integer getUserCount(String username);

    @Select("select * from USER where id = #{id}")
    User selectUserById(Integer id);

    @Delete("delete from USER where id = #{id}")
    void deleteUserById(Integer id);
}
