package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与用户有关的增删改查操作
 */
public class UserDao {
    private final DaoUtils daoUtils;
    public final String USER_MAPPER_URL = "com.github.hcsp.mybatis.UserMapper";

    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.daoUtils = new DaoUtils(sqlSessionFactory);
    }

    public String getUserMapperSQLUrl(String sqlId) {
        return USER_MAPPER_URL + "." + sqlId;
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
        return daoUtils.invokeSqlSession(sqlSession -> {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            params.put("limit", pageSize);
            params.put("offset", (pageNum - 1) * pageSize);
            List<User> users = sqlSession.selectList(getUserMapperSQLUrl("queryUsers"), params);
            double userTotal = sqlSession.selectOne(getUserMapperSQLUrl("queryUsersCount"), username);
            int totalPages = ((Double) Math.ceil(userTotal / pageSize)).intValue();
            return Pagination.pageOf(users, pageSize, pageNum, totalPages);
        });
    }

    /**
     * 向数据库中批量插入若干个用户，注意，请使用批处理操作。
     *
     * @param users 待插入的用户列表
     */
    public void batchInsertUsers(List<User> users) {
        daoUtils.invokeSqlSession(sqlSession ->
                sqlSession.insert(getUserMapperSQLUrl("batchInsertUsers"), users), true);
    }

    /**
     * 根据用户的ID更新一个用户的数据，更新传入的user中所有不为null的字段。
     *
     * @param user 要修改的用户信息，其id必须不为null
     */
    public void updateUser(User user) {
        daoUtils.invokeSqlSession(sqlSession -> sqlSession.update(
                getUserMapperSQLUrl("updateUser"), user), true);
    }

    /**
     * 删除一个用户。
     *
     * @param id 待删除的用户ID
     */
    public void deleteUserById(Integer id) {
        daoUtils.invokeSqlSession(sqlSession -> sqlSession.delete(getUserMapperSQLUrl("deleteUser"), id),
                true);
    }

    /**
     * 根据ID获取一个用户，如果该用户不存在，返回null
     *
     * @param id 待获取的用户ID
     * @return 对应的用户
     */
    public User selectUserById(Integer id) {
        return daoUtils.invokeSqlSession(sqlSession ->
                sqlSession.selectOne(getUserMapperSQLUrl("queryUser"), id)
        );
    }
}
