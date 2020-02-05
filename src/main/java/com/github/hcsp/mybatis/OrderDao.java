package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Order;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderDao {
    private final SqlSessionFactory sqlSessionFactory;

    public OrderDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 查询订单信息，只查询用户名、商品名齐全的订单，即INNER JOIN方式
     * 结果请参考：https://github.com/hcsp/practise-select-sql/blob/a450e2a/src/main/java/com/github/hcsp/sql/Sql.java#L172
     *
     * @return 所有的订单信息
     */
    public List<Order> getInnerJoinOrders() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            return sqlSession.selectList("MyMapper.getInnerJoinOrders");
        }
    }
}
