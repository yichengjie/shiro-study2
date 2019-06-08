package com.yicj.study.shiro.shiro;

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
    	logger.info("-------------->【shiro】授权认证：");
        //获取登录用户名
        User user= (User) principalCollection.getPrimaryPrincipal();
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
    	logger.info("-------------->【shiro】登陆验证:");
        //获取用户信息
        String username = token.getPrincipal().toString();
        String password = new String((char[])token.getCredentials()); //[C@b99a89
        System.out.println("--------------> password : " + password);
        User user = userService.findRolesAndPermissionsByUserNameAndPassword(
        		username, password) ;
        if (user == null) {
            //用户不存在就抛出异常
            throw new UnknownAccountException("用户名或密码错误!") ;
        } 
        if(user.getState() ==1) {
        	throw new LockedAccountException("账号被锁!") ;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = 
        	new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}