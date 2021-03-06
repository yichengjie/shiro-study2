package com.yicj.study.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.yicj.study.shiro.config.MyExceptionResolver;

@SpringBootApplication
@MapperScan("com.yicj.study.shiro.mapper")
public class Application {
	//https://blog.csdn.net/u013615903/article/details/78781166/
	//https://www.cnblogs.com/ll409546297/p/7815409.html
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args) ;
	}
	
	//注册统一异常处理bean
    @Bean
    public MyExceptionResolver myExceptionResolver() {
        return new MyExceptionResolver();
    }
}
