package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-04-25
 */
public interface UserMapper {

    @Select("select count(*) from user")
    int count();

    @Select("<script> select * from user where 1=1 <if test='userName != null'> and name =#{userName} </if></script> ")
    List<User> query(String userName);

    int batchInsertUsers(List<User> users);

    void updateUser(User user);

    @Select("select * from user where id = #{id}")
    User selectUserById(Integer id);

    @Delete("delete from user where id = #{id}")
    void deleteUserById(Integer id);
}
