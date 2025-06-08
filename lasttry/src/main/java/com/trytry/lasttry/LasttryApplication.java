package com.trytry.lasttry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.trytry.lasttry.mapper") // 打jar包需要
@SpringBootApplication
public class LasttryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LasttryApplication.class, args);
    }

}
