package com.demo.crossplatform.service.impl;

import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.mapper.UserMapper;
import com.demo.crossplatform.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
