
package com.wemarket.cc.integration.dao.support;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 抽象sao实现类。
 *
 * @author jonyang
 *
 */
public abstract class AbstractDAO {

    @Autowired
    @Qualifier("sqlSessionFactory")
    protected SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    protected abstract SqlSession getSession();

}