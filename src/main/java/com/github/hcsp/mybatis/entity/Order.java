package com.github.hcsp.mybatis.entity;

import java.math.BigDecimal;

/** 一个订单 */
public class Order {
    private Integer id;
    /** 订单中的用户 */
    private User user;
    /** 订单中的商品 */
    private Goods goods;
    /** 订单中的总成交金额 */
    private BigDecimal totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id="
                + id
                + ", user="
                + user
                + ", goods="
                + goods
                + ", totalPrice="
                + totalPrice
                + '}';
    }
}
