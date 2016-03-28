package com.quowl.quowl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.quowl.quowl.repository")
@EnableTransactionManagement
@PropertySource("classpath:properties/db.properties")
@ComponentScan("com.quowl.quowl")
public class DatabaseConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(getHibernateProperties());

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/hibernate.properties");
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find 'hibernate.properties' in classpath!");
        }
    }

/*    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getRequiredProperty("db.dataSourceClassName"));
        config.addDataSourceProperty("url", env.getRequiredProperty("db.url"));
        config.addDataSourceProperty("databaseName", env.getRequiredProperty("db.name"));
        config.addDataSourceProperty("user", env.getRequiredProperty("db.username"));
        config.addDataSourceProperty("password", env.getRequiredProperty("db.password"));
        config.addDataSourceProperty("encoding", "utf8");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTimeout(30000);

        config.setMaximumPoolSize(20);

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }*/

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
//        basicDataSource.setUrl(env.getRequiredProperty("db.amazon.url"));
        basicDataSource.setUrl(env.getRequiredProperty("db.url"));
//        basicDataSource.setUsername(env.getRequiredProperty("db.amazon.username"));
        basicDataSource.setUsername(env.getRequiredProperty("db.username"));
//        basicDataSource.setPassword(env.getRequiredProperty("db.amazon.password"));
        basicDataSource.setPassword(env.getRequiredProperty("db.password"));

        basicDataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
        basicDataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
        basicDataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
        basicDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        basicDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        basicDataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
        basicDataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return basicDataSource;
    }

}
