package com.yicj.study.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yicj.study.shiro.mapper")
public class Application {
	//https://blog.csdn.net/u013615903/article/details/78781166/
	//https://www.cnblogs.com/expiator/p/8651798.html
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args) ;
		
	}
}
