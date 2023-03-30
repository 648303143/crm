package com.uestc.crm.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.UserMapper;

import com.uestc.crm.pojo.UserPO;

import com.uestc.crm.service.UserService;
import com.uestc.crm.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String SALT = "zqy";

    /**
     * 用户注册
     *
     * @param email         邮箱
     * @param password      用户密码
     * @param checkPassword 用户检验密码
     * @param username      用户名字
     * @return
     */
    @Override
    public long userRegister(String username, String password, String checkPassword, String email) {
        //1.校验
        if (!StrUtil.isAllNotBlank(email, password, checkPassword, username)) {
            throw new RuntimeException("请求参数为空");
        }
        //密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new RuntimeException("密码和校验密码相同");
        }
        //账户邮箱不能重复
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getEmail, email);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("账户邮箱不能重复");
        }
        //昵称不能重复
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, username);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("昵称不能重复");
        }

        //2.加密
        String encryptPassword = Base64.encode((SALT + password).getBytes(StandardCharsets.UTF_8));
        //3.插入数据
        UserPO user = new UserPO();
        user.setEmail(email);
        user.setPassword(encryptPassword);
        user.setUsername(username);

        int insert = userMapper.insert(user);
        if (insert != 1) {
            return -1;
        }
        return user.getId();
    }


    @Override
    public String userDoLogin(String username, String password, Long loginTime) {
        //1.校验
        if (!StrUtil.isAllNotBlank(username, password)) {
            throw new RuntimeException("参数不能为空");
        }

        //2.加密
        String encryptPassword = Base64.encode((SALT + password).getBytes(StandardCharsets.UTF_8));
        //判断账户是否存在
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getUsername, username);
        UserPO user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed");
            throw new RuntimeException("账户不存在");
        }
        if (!user.getPassword().equals(encryptPassword)) {
            log.info("user login failed");
            throw new RuntimeException("密码不正确");
        }
        //3.记录用户登录状态
        String token = TokenUtil.sign(username, loginTime);
        //断言token不为空，并存入redis
        assert token != null;
        redisTemplate.opsForValue().set(token, user, 12, TimeUnit.HOURS);

        return token;
    }

    public UserPO getUserInfo(String token) {
        UserPO userPO = (UserPO) redisTemplate.opsForValue().get(token);
        return userPO;
    }

    public Boolean logout(String token) {
        Boolean delete = redisTemplate.delete(token);
        return delete;
    }
}
