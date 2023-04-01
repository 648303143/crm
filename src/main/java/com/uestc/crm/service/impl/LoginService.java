package com.uestc.crm.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uestc.crm.mapper.UserMapper;
import com.uestc.crm.model.LoginUser;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/1 11:22
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

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
    public long userRegister(String username, String password, String checkPassword, String email) {
        //1.校验
        if (!StrUtil.isAllNotBlank(email, password, checkPassword, username)) {
            throw new RuntimeException("请求参数为空");
        }
        //密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new RuntimeException("密码和校验密码不相同");
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


    public String userDoLogin(String username, String password) {
        //1.校验
        if (!StrUtil.isAllNotBlank(username, password)) {
            throw new RuntimeException("参数不能为空");
        }

        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            AuthenticationContextHolder.clearContext();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
