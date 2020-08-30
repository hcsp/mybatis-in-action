package com.github.hcsp.mybatis.entity;

import java.util.List;

/** 数据库的分页结果， */
public class Pagination<T> {
    private final List<T> items;
    private final int pageSize;
    private final int pageNum;
    private final int totalPage;

    private Pagination(List<T> items, int pageSize, int pageNum, int totalPage) {
        this.items = items;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public static <T> Pagination<T> pageOf(
            List<T> items, int pageSize, int pageNum, int totalPage) {
        return new Pagination<>(items, pageSize, pageNum, totalPage);
    }

    @Override
    public String toString() {
        return "Pagination{"
                + "items="
                + items
                + ", pageSize="
                + pageSize
                + ", pageNum="
                + pageNum
                + ", totalPage="
                + totalPage
                + '}';
    }
}
