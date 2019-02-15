package com.wemarket.cc.integration.dao.support;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;


/**
 * 抽象简单sao实现类。
 *
 * @author jonyang
 *
 */
public class AbstractSimpleDAO extends AbstractDAO {

    protected SqlSession getSession() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true);
        return sqlSession;
    }

}