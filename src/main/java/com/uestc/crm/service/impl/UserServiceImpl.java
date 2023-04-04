package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.UserMapper;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.query.UserListQuery;
import com.uestc.crm.service.UserService;
import com.uestc.crm.util.SecurityUtils;
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

    @Override
    public IPage<UserPO> listUser(UserListQuery query) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(query.getUsername()), UserPO::getUsername, query.getUsername())
                .eq(StrUtil.isNotEmpty(query.getPhonenumber()), UserPO::getPhonenumber, query.getPhonenumber());
        IPage<UserPO> page = userMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }

    public void checkUnique(UserPO user) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, user.getUsername())
                .ne(user.getId() != null, UserPO::getId, user.getId());
        Long usernameCount = userMapper.selectCount(queryWrapper);
        if (usernameCount > 0) {
            throw new RuntimeException();
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getPhonenumber, user.getPhonenumber())
                .ne(user.getId() != null, UserPO::getId, user.getId());
        Long phonecount = userMapper.selectCount(queryWrapper);
        if (phonecount > 0) {
            throw new RuntimeException();
        }
    }

    public int addUser(UserPO userPO) {
        checkUnique(userPO);
        userPO.setPassword(SecurityUtils.encryptPassword(userPO.getPassword()));
        return userMapper.insert(userPO);
    }

    public int updateUser(UserPO userPO) {
        checkUnique(userPO);
        userPO.setPassword(SecurityUtils.encryptPassword(userPO.getPassword()));
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, userPO.getUsername());
        return userMapper.update(userPO, queryWrapper);
    }

    public int deleteUser(String username) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, username);
        return userMapper.delete(queryWrapper);
    }

}
