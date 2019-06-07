package com.yicj.mybatis2.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.shiro.Application;
import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void testSelectCount(){
       Integer count =  userService.count() ;
        System.out.println("============> count : " +count);
    }
    

    @Test
    public void testInsert(){
        User user = new User() ;
        user.setName("yicj");
        user.setEmail("626659321@qq.com");
        user.setAge(31);
        user.setManagerId(1088248166370832385l);
        user.setCreateTime(LocalDateTime.now());
        userService.save(user) ;
    }

    @Test
    public void testSelectByMap(){
        ////注意map中的key为表中字段名而不是Entity的属性名，value为值
        Map<String,Object> map = new HashMap<>() ;
        map.put("manager_id",1088248166370832385L) ;
        Collection<User> users =   userService.listByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testQueryByWrapper(){
        QueryWrapper <User> queryWrapper = Wrappers.query() ;
        //数据中的列名，而不是字段属名
        queryWrapper.like("name","雨") ;
        queryWrapper.gt("age",30) ;
        List<User> list = userService.list(queryWrapper) ;
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectPage(){
        QueryWrapper<User> queryWrapper = Wrappers.query() ;
        queryWrapper.gt("age",30) ;
        IPage<User> page = new Page<User>(1,2) ;
        IPage<User> result = userService.page(page,queryWrapper) ;
        List<User> users = result.getRecords() ;
        users.forEach(System.out::println);

        System.out.println("总页数：" + result.getPages());
        System.out.println("总记录数："+ result.getTotal());
    }

}
