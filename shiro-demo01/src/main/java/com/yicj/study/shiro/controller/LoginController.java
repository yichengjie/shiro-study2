package com.yicj.study.shiro.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	//退出的时候是get请求，主要是用于退出
	@GetMapping("/login")
	public String login() {
		return "login" ;
	}
	
	public String login(@RequestBody Map <String,Object>map) {
		//添加用户认证信息
		Subject subject = SecurityUtils.getSubject(); 
		UsernamePasswordToken token = new UsernamePasswordToken(
				  map.get("username").toString(),
				  map.get("password").toString()) ;
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(token);
		return "login" ;
	}
	
	 //登出
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }
    //错误页面展示
    @GetMapping("/error")
    public String error(){
        return "error ok!";
    }
    
    //注解的使用
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create(){
        return "Create success!";
    }
	
}
