package com.wemarket.cc.common.autoconfigure;

import com.wemarket.cc.common.exception.SysException;
//import cn.webank.mumble.framework.security.util.MumbleRSAUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 公共配置类，所有模块共用。
 * 
 * @author lynndu
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan
@EnableScheduling
@EnableAsync
@EnableCaching
public class CcConfiguration extends WebMvcConfigurerAdapter implements SchedulingConfigurer {


    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.initial-size}")
    private int jdbcInitialSize = 5;

    @Value("${jdbc.min-idle}")
    private int jdbcMinIdle;

    @Value("${jdbc.max-active}")
    private int jdbcMaxActive;

    @Value("${jdbc.max-wait}")
    private int jdbcMaxWait;

    @Value("${jdbc.time-between-eviction-runs-millis}")
    private int jdbcTimeBetweenEvictionRunsMillis;

    @Value("${jdbc.min-evictable-idle-time-millis}")
    private int jdbcMinEvictableIdleTimeMillis;

    @Value("${jdbc.validation-query}")
    private String jdbcValidationQuery;

    @Value("${jdbc.validation-query-timeout}")
    private int validationQueryTimeout = 2;


    @Value("${jdbc.test-while-idle:true}")
    private boolean jdbcTestWhileIdle = true;

    @Value("${jdbc.test-on-borrow:false}")
    private boolean jdbcTestOnBorrow = false;

    @Value("${jdbc.test-on-return:false}")
    private boolean jdbcTestOnReturn = false;

    @Value("${jdbc.prepared-statements:true}")
    private boolean jdbcPoolPreparedStatements = true;

    @Value("${jdbc.max-pool-prepared-statement-per-connection-size:20}")
    private int jdbcMaxPoolPreparedStatementPerConnectionSize = 20;

    @Value("${jdbc.filters:'stat'}")
    private String jdbcFilters = "stat, slf4j";

    @Value("${multipart.maxFileSize}")
    private String maxFileSize;

    @Value("${multipart.maxRequestSize}")
    private String maxRequestSize;

    @Bean(name = "messageSource")
    public MessageSource messageResource() {
        ReloadableResourceBundleMessageSource messageResource =
            new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:messages");
        messageResource.setDefaultEncoding("UTF-8");
        messageResource.setCacheSeconds(3600);
        return messageResource;
    }

    @Bean(name = "frontTaskExecutor")
    public ThreadPoolTaskExecutor frontTaskExecutor(
        @Value("${front.threadpool.core-pool-size:48}") int frontCorePoolSize,
        @Value("${front.threadpool.keep-alive-seconds:60}") int frontKeepAliveSeconds,
        @Value("${front.threadpool.max-pool-size:48}") int frontMaxPoolSize,
        @Value("${front.threadpool.queue-capacity:300}") int frontQueueCapacity,
        @Value("${front.threadpool.allow-core-thread-timeout:true}") boolean frontAllowCoreThreadTimeOut,
        @Value("${front.threadpool.await-termination-seconds:60}") int frontAwaitTerminationSeconds,
        @Value("${front.threadpool.wait-for-task-to-complete-on-shutdown:true}") boolean waitForTasksToCompleteOnShutdown) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(frontCorePoolSize);
        executor.setKeepAliveSeconds(frontKeepAliveSeconds);
        executor.setMaxPoolSize(frontMaxPoolSize);
        executor.setQueueCapacity(frontQueueCapacity);
        executor.setAllowCoreThreadTimeOut(frontAllowCoreThreadTimeOut);
        executor.setAwaitTerminationSeconds(frontAwaitTerminationSeconds);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        try {
            DruidDataSource ds = new DruidDataSource();
            ds.setUrl(jdbcUrl);
            ds.setUsername(jdbcUser);
            ds.setPassword(jdbcPassword);
            ds.setInitialSize(jdbcInitialSize);
            ds.setMinIdle(jdbcMinIdle);
            ds.setMaxActive(jdbcMaxActive);
            ds.setMaxWait(jdbcMaxWait);
            ds.setTimeBetweenEvictionRunsMillis(jdbcTimeBetweenEvictionRunsMillis);
            ds.setMinEvictableIdleTimeMillis(jdbcMinEvictableIdleTimeMillis);
            ds.setValidationQuery(jdbcValidationQuery);
            ds.setValidationQueryTimeout(validationQueryTimeout);
            ds.setTestWhileIdle(jdbcTestWhileIdle);
            ds.setTestOnBorrow(jdbcTestOnBorrow);
            ds.setTestOnReturn(jdbcTestOnReturn);
            ds.setPoolPreparedStatements(jdbcPoolPreparedStatements);
            ds.setMaxPoolPreparedStatementPerConnectionSize(
                jdbcMaxPoolPreparedStatementPerConnectionSize);

            ds.setFilters(jdbcFilters);
            return ds;
        } catch (SQLException e) {
            throw new SysException("datasource construct failed", e);
        }



    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource());

        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        factory.setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"));

        try {
    		ArrayList<Resource> resourceMapping = new ArrayList<Resource>();
	  		Collections.addAll(resourceMapping, (new PathMatchingResourcePatternResolver()).getResources("classpath:com/wemarket/cc/integration/dao/*Mapper.xml"));
	  		factory.setMapperLocations(resourceMapping.toArray(new Resource[0]));
            return factory.getObject();
        } catch (Exception e) {
        		e.printStackTrace();
            throw new SysException("sqlSessionFactory construct fail", e);
        }
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    
    //附件上限控制
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        
        return factory.createMultipartConfig();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(setTaskScheduler());
    }

    @Bean(name = "taskScheduler")
    public ThreadPoolTaskScheduler setTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(48);
        return scheduler;
    }
}
