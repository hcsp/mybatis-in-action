package com.github.hcsp.mybatis.Mapper;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;

public interface SelectUserByIDMapper {
    @Select("select * from user where id = #{id}")
    User selectUserById(int id);
}
