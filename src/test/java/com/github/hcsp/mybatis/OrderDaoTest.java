package com.github.hcsp.mybatis;

import static com.github.hcsp.mybatis.UserDaoTest.getSqlSessionFactoryAfterFlywayCleanMigrate;

import com.github.hcsp.mybatis.entity.Goods;
import com.github.hcsp.mybatis.entity.Order;
import com.github.hcsp.mybatis.entity.User;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderDaoTest {
    private OrderDao orderDao;

    @BeforeEach
    public void setUp() throws Exception {
        orderDao = new OrderDao(getSqlSessionFactoryAfterFlywayCleanMigrate());
    }

    @Test
    public void testGetOrder() {
        final List<Order> orders = orderDao.getInnerJoinOrders();
        Assertions.assertEquals(
                Arrays.asList(50, 10, 20, 80, 2000, 20),
                orders.stream()
                        .map(Order::getTotalPrice)
                        .map(BigDecimal::intValue)
                        .collect(Collectors.toList()));
        Assertions.assertEquals(
                Arrays.asList("zhangsan", "lisi", "lisi", "zhangsan", "zhangsan", "zhangsan"),
                orders.stream()
                        .map(Order::getUser)
                        .map(User::getName)
                        .collect(Collectors.toList()));
        Assertions.assertEquals(
                Arrays.asList("goods1", "goods1", "goods1", "goods2", "goods2", "goods3"),
                orders.stream()
                        .map(Order::getGoods)
                        .map(Goods::getName)
                        .collect(Collectors.toList()));
    }
}
