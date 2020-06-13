package com.github.hcsp.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.function.Function;

public class DaoUtils {
    private final SqlSessionFactory sqlSessionFactory;

    public DaoUtils(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public <T> T invokeSqlSession(Function<SqlSession, T> function) {
        return invokeSqlSession(function, false);
    }

    public <T> T invokeSqlSession(Function<SqlSession, T> function, boolean autoCommit) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(autoCommit)) {
            return function.apply(sqlSession);
        }
    }

}
