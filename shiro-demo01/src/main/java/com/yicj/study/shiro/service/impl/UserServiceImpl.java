package com.yicj.study.shiro.service.impl;

import com.yicj.study.shiro.entity.User;
import com.yicj.study.shiro.mapper.UserMapper;
import com.yicj.study.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
