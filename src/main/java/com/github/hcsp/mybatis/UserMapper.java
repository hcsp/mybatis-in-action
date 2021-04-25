package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.User;
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

    @Select("select * from user")
    List<User> query(String userName);

}
