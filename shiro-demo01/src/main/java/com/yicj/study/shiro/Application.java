package com.yicj.study.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yicj.study.shiro.mapper")
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args) ;
		
	}
}
