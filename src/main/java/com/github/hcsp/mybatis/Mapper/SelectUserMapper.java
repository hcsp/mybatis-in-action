package com.github.hcsp.mybatis.Mapper;

import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;

public interface SelectUserMapper {
    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User selectUserById(Integer id);
}
