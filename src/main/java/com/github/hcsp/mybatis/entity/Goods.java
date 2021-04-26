package com.github.hcsp.mybatis.entity;

import java.math.BigDecimal;

/** 一件商品 */
public class Goods {
    private Integer id;
    private String name;
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
