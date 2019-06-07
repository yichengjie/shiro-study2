package com.yicj.study.shiro.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration //该注解类似于spring配置文件
public class MyBatisConfig {
	
	@Value("${jdbc.driverClassName}")
	private String driverClassName ;
	//
	@Value("${jdbc.url}")
	private String url  ;
	//
	@Value("${jdbc.username}")
	private String username ;
	//
	@Value("${jdbc.password}")
	private String password ;
	//可以通过env.getProperties("key")获取键值对
	//@Autowired
	//private Environment env ;
	/**
	 * 创建数据源
	 * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错 
	 * @return
	 * @throws Exception
	 */
	//@Primary
	@Bean
	public DataSource getDataSource() throws Exception {
		Properties props = new Properties() ;
		props.put("driverClassName", driverClassName) ;
		props.put("url", url) ;
		props.put("username", username) ;
		props.put("password", password) ;
		return DruidDataSourceFactory.createDataSource(props) ;
	}

	@Bean
	public PaginationInterceptor paginationInterceptor(){
		return new PaginationInterceptor() ;
	}
}