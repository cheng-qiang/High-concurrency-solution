package com.chen.concurrency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.chen.concurrency.mapper")
public class MianshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MianshaApplication.class, args);
    }

}
