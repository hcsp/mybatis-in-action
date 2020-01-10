package com.github.hcsp.mybatis.mapper;

import com.github.hcsp.mybatis.entity.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> getInnerJoinOrders();
}
