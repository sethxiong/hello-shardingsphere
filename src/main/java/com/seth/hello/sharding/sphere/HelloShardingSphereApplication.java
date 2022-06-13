package com.seth.hello.sharding.sphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;

@MapperScan(basePackages = "com.seth.hello.sharding.sphere.mapper")
@SpringBootApplication
public class HelloShardingSphereApplication implements CommandLineRunner {

    @Resource
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(HelloShardingSphereApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("======================");
        System.out.println(dataSource);
    }
}
