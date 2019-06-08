package com.yicj.study.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yicj.study.shiro.entity.Permission;
import com.yicj.study.shiro.entity.Role;
import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.service.UserService;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm{
	private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class) ;

    //用于用户查询
    @Autowired
    private UserService userService;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
    		PrincipalCollection principalCollection) {
    	logger.info("---------------------------->【shiro】授权认证：");
        //获取登录用户名
        String name= (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = userService.findRolesAndPermissionsByUserName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role:user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission:role.getPermissions()) {
                //添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
    		throws AuthenticationException {
    	logger.info("---------------------------->【shiro】登陆验证:");
    	//加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        /*if (token.getPrincipal() == null) {
            return null;
        }*/
        //获取用户信息
        String name = token.getPrincipal().toString();
        User user = userService.findUserByName(name) ;
        if (user == null) {
            //用户不存在就抛出异常
            throw new UnknownAccountException("账号不存在!") ;
        } 
        if(user.getState() ==1) {
        	throw new LockedAccountException("账号被锁!") ;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = 
        		new SimpleAuthenticationInfo(name, 
        				user.getPassword().toString(), getName());
        return simpleAuthenticationInfo;
    }
}