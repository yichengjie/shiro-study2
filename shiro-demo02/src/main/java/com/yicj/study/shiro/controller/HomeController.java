package com.yicj.study.shiro.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yicj.study.shiro.vo.UserInfoVo;

@RestController
@RequestMapping("/apis/")
public class HomeController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class) ;
	
	// 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	//未登录时，请求后台资源后，会走到这里
	@GetMapping(value = "/unauth")
	public Map<String, Object> unauth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "1000000");
		map.put("msg", "未登录");
		return map;
	}

	//当访问了无权限访问的资源时
	@GetMapping("/403")
	public Map<String, Object> noauth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "1000002");
		map.put("msg", "无权访问");
		return map;
	}

	@PostMapping("/ajaxLogin")
	public String ajaxLogin(@RequestBody UserInfoVo userInfo) {
		JSONObject jsonObject = new JSONObject();
		Subject subject = SecurityUtils.getSubject();
		logger.info("ajaxLogin: " +userInfo);
		UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());
		try {
			subject.login(token);
			jsonObject.put("token", subject.getSession().getId());
			jsonObject.put("msg", "登录成功");
		} catch (IncorrectCredentialsException e) {
			jsonObject.put("msg", "密码错误");
		} catch (LockedAccountException e) {
			jsonObject.put("msg", "登录失败，该用户已被冻结");
		} catch (AuthenticationException e) {
			jsonObject.put("msg", "该用户不存在");
		} catch (Exception e) {
			logger.error("登录失败:",e);
			jsonObject.put("msg", "其他错误!") ;
		}
		return jsonObject.toString();

	}

}
