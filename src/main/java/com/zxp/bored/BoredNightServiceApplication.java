package com.zxp.bored;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zxp.bored.dao")
public class BoredNightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoredNightServiceApplication.class, args);
    }

}
