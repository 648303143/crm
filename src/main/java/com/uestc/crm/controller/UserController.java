package com.uestc.crm.controller;

import cn.hutool.core.util.StrUtil;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.vo.LoginVO;
import com.uestc.crm.vo.RegisterVO;
import com.uestc.crm.service.impl.UserServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/6 18:55
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /**
     * 注册功能
     *
     * @param registerVO
     * @return
     */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterVO registerVO) {
        if (registerVO == null) {
            return Result.error(ExceptionCodeEnum.EMPTY_PARAM);
        }
        String username = registerVO.getUsername();
        String password = registerVO.getPassword();
        String checkPassword = registerVO.getCheckPassword();
        String email = registerVO.getEmail();

        if (!StrUtil.isAllNotBlank(email, password, checkPassword, username)) {
            return Result.error(ExceptionCodeEnum.EMPTY_PARAM, "参数不全");
        }
        long result = 0;
        try {
            result = userService.userRegister(username, password, checkPassword, email);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success(result);
    }

    @PostMapping("/login")
    public Result<String> Login(@RequestBody LoginVO loginVO) {
        if (loginVO == null) {
            return Result.error(ExceptionCodeEnum.EMPTY_PARAM);
        }
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        Long loginTime = loginVO.getLoginTime();
        if (!StrUtil.isAllNotBlank(username, password)) {
            return Result.error(ExceptionCodeEnum.EMPTY_PARAM);
        }
        String token;
        try {
            token = userService.userDoLogin(username, password, loginTime);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success(token);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody String token) {
        try {
            userService.logout(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }

        return Result.success();
    }

    @PostMapping("/info")
    public Result<UserPO> getUserInfo(@RequestBody String token) {
        UserPO userInfo;
        try {
            userInfo = userService.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }

        return Result.success(userInfo);
    }

}
