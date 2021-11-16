package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 与用户有关的增删改查操作
 */
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;

    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Pagination<User> getUserByPage(String username, int pageSize, int pageNum) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", username);
        params.put("limit", pageSize);
        params.put("offset", (pageNum - 1) * pageSize);
        List<User> users = sqlSession.selectList("db.mapper.UserMapper.getUserByPage", params);
        Integer totalItems = sqlSession.selectOne("db.mapper.UserMapper.countPage", username);
        int totalPage = (totalItems % pageSize == 0) ? totalItems / pageSize : totalItems / pageSize + 1;
        return Pagination.pageOf(users, pageSize, pageNum, totalPage);
    }

    public void batchInsertUsers(List<User> users) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            HashMap<Object, Object> params = new HashMap<>();
            params.put("users", users);
            sqlSession.insert("db.mapper.UserMapper.batchInsertUser", params);
        }
    }

    public void updateUser(User user) {
        if (user.getId() != null) {
            try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
                sqlSession.update("db.mapper.UserMapper.updateUser", user);
            }
        }
    }

    public void deleteUserById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            sqlSession.delete("db.mapper.UserMapper.deleteUserById", id);
        }
    }

    public User selectUserById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return sqlSession.selectOne("db.mapper.UserMapper.selectUser", params);
        }
    }
}
