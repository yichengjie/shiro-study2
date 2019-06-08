package com.yicj.study.shiro.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.yicj.study.shiro.vo.UserInfoVo;

@RestController
@RequestMapping("/apis/")
public class HomeController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class) ;
	
	// 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	//未登录时，请求后台资源后，会走到这里
	@GetMapping(value = "/unlogin")
	public Map<String, Object> unauth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "10000");
		map.put("msg", "未登录");
		return map;
	}

	@PostMapping("/ajaxLogin")
	public String ajaxLogin(@RequestBody UserInfoVo userInfo) {
		JSONObject jsonObject = new JSONObject();
		Subject subject = SecurityUtils.getSubject();
		logger.info("ajaxLogin: " +userInfo);
		UsernamePasswordToken token = new UsernamePasswordToken(
				userInfo.getUsername(), userInfo.getPassword());
		subject.login(token);
		//登录成功以后，向前台发送一个token，shiro根绝这个判断登录使用
		//D00668789F4033FE75E81631B805846C
		//token会写入浏览器的sessionId中
		//写到浏览器后:JSESSIONID=D00668789F4033FE75E81631B805846C
		jsonObject.put("token", subject.getSession().getId());
		jsonObject.put("msg", "登录成功");
		return jsonObject.toString();
	}

}
