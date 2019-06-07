package com.yicj.study.shiro.service;

import com.yicj.study.shiro.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
public interface UserService extends IService<User> {
	public User findRolesAndPermissionsByUserName(String name) ;
	public User findUserByName(String name) ;
}
