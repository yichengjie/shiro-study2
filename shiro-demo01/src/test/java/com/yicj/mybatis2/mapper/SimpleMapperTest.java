package com.yicj.mybatis2.mapper;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yicj.study.shiro.Application;
import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.service.UserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimpleMapperTest {
	@Autowired
    private UserService userService ;

    @Test
    public void testSelectList(){
    	List<User> users = userService.list() ;
    	users.forEach(System.out::println);
    }
    
    @Test
    public void testFindByNameWithRolesAndPermissions() {
    	String name = "test" ;
    	User user = userService.findRolesAndPermissionsByUserName(name);
    	System.out.println(user);
    }
}
