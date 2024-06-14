package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 与用户有关的增删改查操作
 */
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;
    private int totalCount;

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
        return execute(session -> {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            params.put("offset", (pageNum - 1) * pageSize);
            params.put("limit", pageSize);
            List<User> users = session.selectList("com.github.hcsp.mybatis.mappers.UserMapper.selectUserByPage", params);
            int totalCount = session.selectOne("com.github.hcsp.mybatis.mappers.UserMapper.countUser", username);
            return Pagination.pageOf(
                    users, pageSize, pageNum,
                    totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1
            );
        });
    }

    /**
     * 向数据库中批量插入若干个用户，注意，请使用批处理操作。
     *
     * @param users 待插入的用户列表
     */
    public void batchInsertUsers(List<User> users) {
        execute(session -> session.insert("com.github.hcsp.mybatis.mappers.UserMapper.batchInsertUsers", users));
    }

    /**
     * 根据用户的ID更新一个用户的数据，更新传入的user中所有不为null的字段。
     *
     * @param user 要修改的用户信息，其id必须不为null
     */
    public void updateUser(User user) {
        execute(session -> session.update("com.github.hcsp.mybatis.mappers.UserMapper.updateUser", user));
    }

    /**
     * 删除一个用户。
     *
     * @param id 待删除的用户ID
     */
    public void deleteUserById(Integer id) {
        execute(session -> session.delete("com.github.hcsp.mybatis.mappers.UserMapper.deleteUserById", id));
    }

    /**
     * 根据ID获取一个用户，如果该用户不存在，返回null
     *
     * @param id 待获取的用户ID
     * @return 对应的用户
     */
    public User selectUserById(Integer id) {
        return execute(session -> session.selectOne("com.github.hcsp.mybatis.mappers.UserMapper.selectUserById", id));
    }

    private <R> R execute(Function<SqlSession, R> consumer) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return consumer.apply(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
