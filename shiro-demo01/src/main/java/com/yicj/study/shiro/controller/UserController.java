package com.yicj.study.shiro.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService ;
	
	@GetMapping("/getAllUser")
	public List<User> getAllUser(){
		 List<User> list = userService.list();
		 return list ;
	}
}

