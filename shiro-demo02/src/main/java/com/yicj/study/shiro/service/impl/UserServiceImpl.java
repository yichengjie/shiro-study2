package com.yicj.study.shiro.service.impl;

import com.yicj.study.shiro.entity.Permission;
import com.yicj.study.shiro.entity.Role;
import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.mapper.UserMapper;
import com.yicj.study.shiro.service.PermissionService;
import com.yicj.study.shiro.service.RoleService;
import com.yicj.study.shiro.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	// 查询用户通过用户名
	@Override
	public User findRolesAndPermissionsByUserName(String username) {
		// 1.根绝用户名查询用户
		QueryWrapper<User> userWrapper = Wrappers.query();
		userWrapper.eq("name", username);
		User user = getOne(userWrapper);
		this.fillRoleAndPermissionByUser(user);
		return user;
	}

	//填充角色和权限
	private void fillRoleAndPermissionByUser(User user) {
		// 2.查询用户关联的角色
		Integer userid = user.getId();
		QueryWrapper<Role> roleWrapper = Wrappers.query();
		roleWrapper.eq("user_id", userid);
		List<Role> roles = roleService.list(roleWrapper);
		user.setRoles(roles);
		// 3.查询角色关联的全部权限
		roles.forEach(role -> {
			Integer id = role.getId();
			QueryWrapper<Permission> permissionWrapper = Wrappers.query();
			permissionWrapper.eq("role_id", id);
			List<Permission> list = permissionService.list(permissionWrapper);
			role.setPermissions(list);
		});
	}

	@Override
	public User findUserByName(String username) {
		QueryWrapper<User> userWrapper = Wrappers.query();
		userWrapper.eq("name", username);
		User user = getOne(userWrapper);
		return user;
	}

	@Override
	public User findRolesAndPermissionsByUserNameAndPassword(
			String username,String password) {
		// 1.根绝用户名查询用户
		QueryWrapper<User> userWrapper = Wrappers.query();
		userWrapper.eq("name", username);
		//这里可以对密码加密等
		userWrapper.eq("password", password) ;
		User user = getOne(userWrapper);
		this.fillRoleAndPermissionByUser(user);
		return user;
	}
}
