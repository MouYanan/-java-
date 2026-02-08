package com.example.eseshopsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsEshopSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsEshopSearchApplication.class, args);
        System.out.println("===== 电商检索项目启动成功！访问：http://localhost:8080 =====");
    }
}