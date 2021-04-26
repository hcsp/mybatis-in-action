package com.github.hcsp.mybatis;

import com.github.hcsp.mybatis.entity.Pagination;
import com.github.hcsp.mybatis.entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws Exception {
        userDao = new UserDao(getSqlSessionFactoryAfterFlywayCleanMigrate());
    }

    public static SqlSessionFactory getSqlSessionFactoryAfterFlywayCleanMigrate()
            throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(
                "jdbc:h2:file:" + projectDir.getAbsolutePath() + "/target/test",
                "root",
                "Jxi1Oxc92qSj");
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();

        String resource = "db/mybatis/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void canInsertBatch() {
        userDao.batchInsertUsers(
                Arrays.asList(
                        new User(null, "abcd", "tel-abcd-1", "addr-abcd"),
                        new User(null, "abcd", "tel-abcd-2", "addr-abcd")));

        Pagination<User> users = userDao.getUserByPage("abcd", 1, 1);
        Assertions.assertEquals(1, users.getItems().size());
        Assertions.assertEquals(1, users.getPageNum());
        Assertions.assertEquals(1, users.getPageSize());
        Assertions.assertEquals(2, users.getTotalPage());
        Assertions.assertEquals("abcd", users.getItems().get(0).getName());
        Assertions.assertEquals("tel-abcd-1", users.getItems().get(0).getTel());
        Assertions.assertEquals("addr-abcd", users.getItems().get(0).getAddress());
        Assertions.assertNotNull(users.getItems().get(0).getId());
    }

    @Test
    public void canGetUserByPage() {
        Pagination<User> users = userDao.getUserByPage(null, 100, 1);
        Assertions.assertEquals(1, users.getPageNum());
        Assertions.assertEquals(100, users.getPageSize());
        Assertions.assertEquals(1, users.getTotalPage());
        Assertions.assertTrue(users.getItems().size() > 3);
    }

    @Test
    public void canDeleteUser() {
        Pagination<User> usersBefore = userDao.getUserByPage(null, 100, 1);
        userDao.deleteUserById(2);
        Pagination<User> usersAfter = userDao.getUserByPage(null, 100, 1);

        Assertions.assertEquals(1, usersBefore.getItems().size() - usersAfter.getItems().size());
    }

    @Test
    public void canUpdateUser() {
        User user = new User(1, "zhangsanfeng", null, null);
        userDao.updateUser(user);
        user = userDao.selectUserById(1);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("zhangsanfeng", user.getName());
        Assertions.assertEquals("tel1", user.getTel());
        Assertions.assertEquals("beijing", user.getAddress());
    }
}
