package com.github.hcsp.mybatis.mapper;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserByPage(Map map);

    int countUserByName(String name);

    void updateUserInfoById(User user);

    @Insert("INSERT INTO USER(ID,NAME,TEL,ADDRESS) VALUES(#{id},#{name},#{tel},#{address})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="ID")
    void insertUser(User user);

    @Delete("DELETE FROM USER WHERE ID = #{id}")
    void deleteUserById(Integer id);

    @Select("SELECT ID,NAME,TEL,ADDRESS FROM USER WHERE ID = #{id}")
    User selectUserById(Integer id);
}
