package com.linlin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * 业务层配置类 (service,aop,tx)
 * <p>
 * 1.service
 * 2.开启aop的注解支持 aspect: @Before @After @AfterReturning @AfterThrowing @Around @Aspect @Order
 * 3.tx声明式事务管理: 1.对应的事务管理器实现 2.开启事务注解支持 @Transactional
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan("com.linlin.service")
public class ServiceJavaConfig {

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }


}
