package com.yicj.study.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
@RestController
@RequestMapping("/apis/user")
public class UserController {
	// 注解的使用
	@RequiresRoles("admin")
	@RequiresPermissions("user:create")
	@GetMapping("/create")
	public Map<String, Object> create() {
		Map<String, Object> map =new HashMap<String, Object>() ;
		map.put("msg", "创建用户信息成功!") ;
		map.put("code", "200") ;
		return map;
	}

	// 注解的使用
	@RequiresRoles("admin")
	@RequiresPermissions("user:view")
	@GetMapping("/view")
	public Map<String, Object>  view() {
		Map<String, Object> map =new HashMap<String, Object>() ;
		map.put("msg", "获取用户信息成功!") ;
		map.put("code", "200") ;
		return map;
	}
}
