package com.lzumetal.myrpc.hello.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liaosi
 * @date 2018-08-20
 */
@SpringBootApplication(scanBasePackages = "com.lzumetal.myrpc")
public class HelloServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloServerApplication.class, args);
    }
}
