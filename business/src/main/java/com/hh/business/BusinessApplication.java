package com.hh.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 只有feign调用，没有本地事务，可以不使用seata的代理数据源
 *
 * @author hh
 * @version 1.0
 * @time 09/01/2024 11:51
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
