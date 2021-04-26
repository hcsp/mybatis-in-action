package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Order;

import java.util.List;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-04-26
 */
public interface OrderMapper {

    List<Order> getInnerJoinOrders();

}
