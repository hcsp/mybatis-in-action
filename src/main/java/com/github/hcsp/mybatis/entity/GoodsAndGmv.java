package com.github.hcsp.mybatis.entity;

import java.math.BigDecimal;

/** 一件商品及其总成交金额 */
public class GoodsAndGmv {
    private Goods goods;
    private BigDecimal gmv;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public BigDecimal getGmv() {
        return gmv;
    }

    public void setGmv(BigDecimal gmv) {
        this.gmv = gmv;
    }

    @Override
    public String toString() {
        return "GoodsAndGmv{" + "goods=" + goods + ", gmv=" + gmv + '}';
    }
}
