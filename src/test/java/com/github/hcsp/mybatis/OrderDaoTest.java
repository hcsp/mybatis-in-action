package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.GoodsAndGmv;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 在运行测试前，你应该使用mvn initialize向数据库灌入测试数据
public class OrderDaoTest {
    private OrderDao orderDao;

    @BeforeEach
    public void setUp() throws Exception {
        String resource = "db/mybatis/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        orderDao = new OrderDao(sqlSessionFactory);
    }

    @Test
    public void testGoodsAndGmv() {
        List<GoodsAndGmv> results = orderDao.getGoodsAndGmv();
        Assertions.assertEquals(
                "2080,80,20,20",
                results.stream().map(r -> r.getGmv().toString()).collect(Collectors.joining(",")));
    }

    @Test
    public void testGetOrder() {
        Assertions.assertEquals(6, orderDao.getInnerJoinOrders().size());
    }
}
