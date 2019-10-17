package com.github.hcsp.mybatis.vo;

/**
 * @author xiaqi
 * @date 2019/10/14
 */
public class UserPaginationCondition {

    private String username;

    private int offset;

    private int count;

    public UserPaginationCondition() {
    }

    public UserPaginationCondition(String username, int offset, int count) {
        this.username = username;
        this.offset = offset;
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
