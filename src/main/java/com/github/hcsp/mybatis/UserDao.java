package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;

import java.util.List;

import com.github.hcsp.mybatis.vo.UserPaginationCondition;
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

    /**
     * 根据传入的参数查找用户名为username的用户，返回分页后的结果。
     *
     * @param username 传入的用户名
     * @param pageSize 分页搜索，每页显示的条数
     * @param pageNum  分页的页码，从1开始
     * @return 查找结果，若username为null，则返回所有用户的列表
     */
    public Pagination<User> getUserByPage(String username, int pageSize, int pageNum) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            if (null == username || "".equals(username)) {
                List<User> list = sqlSession.selectList("com.github.hcsp.mybatis.UserDao.selectAllUsers");
                return Pagination.pageOf(list, pageSize, pageNum, (int) Math.ceil(list.size() * 1.0 / pageSize));
            }
            List<User> res = sqlSession.selectList("com.github.hcsp.mybatis.UserDao.selectUsersByUsername", new UserPaginationCondition(username, (pageNum - 1) * pageSize, pageSize));
            Long numberOfUsers = sqlSession.selectOne("com.github.hcsp.mybatis.UserDao.countUsersByUsername", username);
            return Pagination.pageOf(res, pageSize, pageNum, (int) Math.ceil(numberOfUsers * 1.0 / pageSize));
        }
    }

    /**
     * 向数据库中批量插入若干个用户，注意，请使用批处理操作。
     * ---->Mybatis中SQL的foreach的使用
     *
     * @param users 待插入的用户列表
     */
    public void batchInsertUsers(List<User> users) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.update("com.github.hcsp.mybatis.UserDao.insertUsers", users);
            sqlSession.commit();
        }
    }

    /**
     * 根据用户的ID更新一个用户的数据，更新传入的user中所有不为null的字段。
     * selective update
     * ---->mybatis中if判断标签的使用
     *
     * @param user 要修改的用户信息，其id必须不为null
     */
    public void updateUser(User user) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.update("com.github.hcsp.mybatis.UserDao.updateUser", user);
            // TODO: must?
            sqlSession.commit();
        }
    }

    /**
     * 删除一个用户。
     *
     * @param id 待删除的用户ID
     */
    public void deleteUserById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.selectOne("com.github.hcsp.mybatis.UserDao.deleteUserById", id);
        }
    }

    /**
     * 根据ID获取一个用户，如果该用户不存在，返回null
     *
     * @param id 待获取的用户ID
     * @return 对应的用户
     */
    public User selectUserById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectOne("com.github.hcsp.mybatis.UserDao.selectUserById", id);
        }
    }
}
