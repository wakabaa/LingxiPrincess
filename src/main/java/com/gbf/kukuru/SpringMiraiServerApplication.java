package com.gbf.kukuru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("com.gbf.kukuru.mapper")
@EnableScheduling
public class SpringMiraiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMiraiServerApplication.class, args);
    }

}
