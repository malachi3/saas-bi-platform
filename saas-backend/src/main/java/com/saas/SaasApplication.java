package com.saas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SaaS 多租户平台启动类
 *
 * @author saas
 */
@SpringBootApplication
@MapperScan("com.saas.module.**.mapper")
public class SaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaasApplication.class, args);
    }
}
