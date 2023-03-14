package com.uestc.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.query.LoginVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface UserService extends IService<UserPO> {

    /**
     * 用户注册
     *
     * @param username      用户邮箱
     * @param password      用户密码
     * @param checkPassword 用户检验密码
     * @return
     */
    long userRegister(String username, String password, String checkPassword, String email);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param loginTime
     * @return
     */
    String userDoLogin(String username, String password, Long loginTime);

}