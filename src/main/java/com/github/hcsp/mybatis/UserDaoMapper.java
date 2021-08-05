package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface UserDaoMapper {
    @Delete("delete from USER where id = #{id}")
    void deleteUserById(Integer id);

    @Select("select * from User where id = #{id}")
    User selectUserById(Integer id);
}
