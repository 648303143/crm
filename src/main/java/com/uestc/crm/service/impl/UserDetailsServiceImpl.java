package com.uestc.crm.service.impl;

import com.uestc.crm.model.LoginUser;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.security.context.AuthenticationContextHolder;
import com.uestc.crm.service.UserService;
import com.uestc.crm.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/1 11:13
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuServiceImpl menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO user = userService.getUserByUsername(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new RuntimeException("登录用户：" + username + " 不存在");
        }

        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(UserPO user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.getRoleId() == 1) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.getMenuPermsByRoleId(user.getRoleId()));
        }
        return new LoginUser(user, perms);
    }
}
