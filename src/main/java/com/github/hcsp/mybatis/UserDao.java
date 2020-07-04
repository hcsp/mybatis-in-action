package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
        try (SqlSession session = sqlSessionFactory.openSession()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("offset", (pageNum - 1) * pageSize);
            map.put("limit", pageSize);
            List<User> users = session.selectList("myMapper.getUser", map);
            int count = session.selectOne("myMapper.countUser", username);
            int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return Pagination.pageOf(users, pageSize, pageNum, totalPage);
        }
    }

    /**
     * 向数据库中批量插入若干个用户，注意，请使用批处理操作。
     *
     * @param users 待插入的用户列表
     */
    public void batchInsertUsers(List<User> users) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("users", users);
            session.insert("myMapper.batchInsertUsers", map);
        }
    }

    /**
     * 根据用户的ID更新一个用户的数据，更新传入的user中所有不为null的字段。
     *
     * @param user 要修改的用户信息，其id必须不为null
     */
    public void updateUser(User user) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            session.update("myMapper.updateUser", user);
        }
    }

    /**
     * 删除一个用户。
     *
     * @param id 待删除的用户ID
     */
    public void deleteUserById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            session.delete("myMapper.deleteUserById", id);
        }
    }

    /**
     * 根据ID获取一个用户，如果该用户不存在，返回null
     *
     * @param id 待获取的用户ID
     * @return 对应的用户
     */
    public User selectUserById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("myMapper.selectUserById", id);
        }
    }

    public static void main(String[] args) throws IOException {
        String resource = "db/mybatis/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        UserDao userDao = new UserDao(build);

//        System.out.println(userDao.getUserByPage("zhangsan", 1, 10));

//        User user2 = new User(122, "1cvcv", "lisi1111j", "xxxxxx");
//        User user3 = new User(132, "1dfdfdgfdfdf", "lisi11111", "xxxxxx");
//
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//
//        userDao.batchInsertUsers(users);

//        User user1 = new User(1, "12341ererererererer", null, null);
//        userDao.updateUser(user1);

    }
}
