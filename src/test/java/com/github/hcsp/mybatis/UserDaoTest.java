package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 在运行测试前，你应该使用mvn initialize向数据库灌入测试数据
public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws Exception {
        String resource = "db/mybatis/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        userDao = new UserDao(sqlSessionFactory);
    }

    @Test
    public void canInsertBatch() {
        userDao.insertUsers(
                Arrays.asList(
                        new User(null, "abcd", "tel-abcd", "addr-abcd"),
                        new User(null, "bcde", "tel-bcde", "addr-bcde")));

        Pagination<User> users = userDao.search("bc", 1, 1);
        Assertions.assertEquals(1, users.getItems().size());
        Assertions.assertEquals(1, users.getPageNum());
        Assertions.assertEquals(1, users.getPageSize());
        Assertions.assertEquals(2, users.getTotalPage());
        Assertions.assertEquals("abcd", users.getItems().get(0).getName());
        Assertions.assertEquals("tel-abcd", users.getItems().get(0).getTel());
        Assertions.assertEquals("addr-abcd", users.getItems().get(0).getAddress());
        Assertions.assertNotNull(users.getItems().get(0).getId());
    }

    @Test
    public void canSearch() {
        Pagination<User> users = userDao.search(null, 100, 1);
        Assertions.assertEquals(1, users.getPageNum());
        Assertions.assertEquals(100, users.getPageSize());
        Assertions.assertEquals(1, users.getTotalPage());
        Assertions.assertTrue(users.getItems().size() > 3);
    }

    @Test
    public void canDeleteUser() {
        Pagination<User> usersBefore = userDao.search(null, 100, 1);
        userDao.deleteUserById(2);
        Pagination<User> usersAfter = userDao.search(null, 100, 1);

        Assertions.assertEquals(1, usersBefore.getItems().size() - usersAfter.getItems().size());
    }

    @Test
    public void canUpdateUser() {
        User user = new User(1, "zhangsanfeng", null, null);
        userDao.updateUser(user);
        user = userDao.getUserById(1);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("zhangsanfeng", user.getName());
        Assertions.assertEquals("tel", user.getTel());
        Assertions.assertEquals("beijing", user.getAddress());
    }
}
