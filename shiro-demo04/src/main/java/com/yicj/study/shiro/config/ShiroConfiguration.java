package com.yicj.study.shiro.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yicj.study.shiro.shiro.MySessionManager;
import com.yicj.study.shiro.shiro.MyShiroRealm;

@Configuration
public class ShiroConfiguration {
	private Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class) ;
	@Value("${shiro.host}")
	private String host;
	@Value("${shiro.timeout}")
	private int timeout;

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> map = new HashMap<String, String>();
		// 退出登录
		map.put("/apis/logout", "logout");
		// 登录
		map.put("/apis/ajaxLogin", "anon");
		map.put("/apis/login", "anon");
		map.put("/apis/403", "anon");
		// 对所有用户认证
		map.put("/**", "authc");
		// 如果不配置会自动跳转到login.jsp页面，在没有登录时访问需要权限资源自动跳转的
		shiroFilterFactoryBean.setLoginUrl("/apis/unlogin");
		// 前端控制跳转的话就不需要
		// shiroFilterFactoryBean.setSuccessUrl("/index");
		// springboot集成项目中，这里设置了也不生效，只会在后台打印异常，可以直接全局异常拦截，然后处理
		// shiroFilterFactoryBean.setUnauthorizedUrl("/apis/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	// 将自己的验证方式加入容器
	@Bean
	public MyShiroRealm shiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}
	
	// 自定义sessionManager
	@Bean
	public SessionManager sessionManager() {
		MySessionManager mySessionManager = new MySessionManager();
		mySessionManager.setSessionDAO(redisSessionDAO());
		return mySessionManager;
	}

	// 配置shiro redisManager
	// 使用的是shiro-redis开源插件
	//@Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setTimeout(timeout);
		return redisManager;
	}

	// cacheManager 缓存 redis实现
	// 使用的是shiro-redis开源插件
	@Bean
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	// RedisSessionDAO shiro sessionDao层的实现 通过redis
	// 使用的是shiro-redis开源插件
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	// 开启aop注解支持
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	// Shiro生命周期处理器,这个千万不要加，如果加后通过${key}获取不到值,暂时不知道为啥
	/*@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}*/

	// 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	// 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

}