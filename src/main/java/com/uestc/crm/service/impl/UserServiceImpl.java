package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.UserMapper;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserPO getUserByUsername(String username) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, username);
        UserPO user = userMapper.selectOne(queryWrapper);
        return user;
    }

}
