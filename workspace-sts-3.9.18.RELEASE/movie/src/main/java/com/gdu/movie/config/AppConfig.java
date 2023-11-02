package com.gdu.movie.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@MapperScan(basePackages="com.gdu.movie.dao")
@PropertySource(value="classpath:application.properties")
@Configuration
public class AppConfig {

  @Autowired
  private Environment env;
  
  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
    hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
    hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
    hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
    return hikariConfig;
  }
  
  @Bean
  public HikariDataSource hikariDataSource() {
    return new HikariDataSource(hikariConfig());
  }
  
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(hikariDataSource());
    sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location")));
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
    return sqlSessionFactoryBean.getObject();
  }
  
  @Bean
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }

  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(hikariDataSource());
  }
  
}