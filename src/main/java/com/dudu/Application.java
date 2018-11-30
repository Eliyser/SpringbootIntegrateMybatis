package com.dudu;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Bean(destroyMethod = "close") //destroyMethod = "close"：当数据库连接不使用时就把该连接重新放到数据池中，方便下次调用
    @ConfigurationProperties(prefix="spring.datasource") //直接使用配置文件中前缀为这个的属性来set DataSource的属性
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
























