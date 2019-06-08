package com.yicj.study.shiro.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	//情况1:未登录时，由ShiroConfiguration中配置的setLoginUrl跳转过来
	//情况2:登录失败时由shiro自动跳转到setLoginUrl指定的跳转过来
	@GetMapping("/login")
	public String login(HttpServletRequest request, Map<String, String> map) {
		System.out.println("-------> HomeController.login()");
	    // 登录失败从request中获取shiro处理的异常信息。
	    // shiroLoginFailure:就是shiro异常类的全类名.
	    String exception = (String) request.getAttribute("shiroLoginFailure");
	    System.out.println("exception=" + exception);
	    String msg = "";
	    if (exception != null) {
	        if (UnknownAccountException.class.getName().equals(exception)) {
	            System.out.println("UnknownAccountException -- > 账号不存在：");
	            msg = "UnknownAccountException -- > 账号不存在：";
	        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
	            System.out.println("IncorrectCredentialsException -- > 密码不正确：");
	            msg = "IncorrectCredentialsException -- > 密码不正确：";
	        } else if ("kaptchaValidateFailed".equals(exception)) {
	            System.out.println("kaptchaValidateFailed -- > 验证码错误");
	            msg = "kaptchaValidateFailed -- > 验证码错误";
	        } else {
	            msg = "else >> "+exception;
	            System.out.println("else -- >" + exception);
	        }
	    }
	    map.put("msg", msg);
	    // 此方法不处理登录成功,由shiro进行处理
	    return "/login";
	}
	
	 //登出
    @GetMapping("/index")
    public String index(){
        return "index";
    }
	
	
    //错误页面展示
    @GetMapping("/error")
    public String error(){
        return "error";
    }
    
    //错误页面展示
    @GetMapping("/403")
    public String noauth(){
        return "403";
    }
    
    
    //注解的使用
    @RequiresRoles("admin")
    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create")
    @ResponseBody
    public String create(){
        return "Create success!";
    }
	
}
