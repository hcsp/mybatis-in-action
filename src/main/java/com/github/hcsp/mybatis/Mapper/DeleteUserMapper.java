package com.github.hcsp.mybatis.Mapper;

import org.apache.ibatis.annotations.Delete;

public interface DeleteUserMapper {
    @Delete("DELETE FROM USER WHERE ID = #{id}")
    void deleteUserByID(int id);
}
