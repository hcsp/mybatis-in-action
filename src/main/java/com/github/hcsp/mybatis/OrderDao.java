package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.GoodsAndGmv;
import com.github.hcsp.mybatis.entity.Order;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderDao {
    private final SqlSessionFactory sqlSessionFactory;

    public OrderDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 查询所有商品及其销售额，按照销售额大小从大到小排序
     *
     * @return 商品和销售额，按照销售额降序排列
     */
    public List<GoodsAndGmv> getGoodsAndGmv() {
        return null;
    }

    /**
     * 查询订单信息，只查询用户名、商品名齐全的订单，即INNER JOIN方式
     *
     * @return 所有的订单信息
     */
    public List<Order> getInnerJoinOrders() {
        return null;
    }
}
