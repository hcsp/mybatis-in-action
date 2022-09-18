package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Order;
import com.github.hcsp.mybatis.entity.User;

import java.util.List;
import java.util.Map;

public interface MyMapper {
    List<User> getUserByPage(Map<String, Object> map);

    Integer countUser(String username);

    void batchInsertUsers(Map<String, Object> map);

    void updateUser(User user);

    User selectUserById(Integer id);

    void deleteUserById(Integer id);

    List<Order> getInnerJoinOrders();
}
