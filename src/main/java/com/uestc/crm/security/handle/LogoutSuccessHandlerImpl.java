package com.uestc.crm.security.handle;


import com.google.gson.Gson;
import com.uestc.crm.model.LoginUser;
import com.uestc.crm.service.impl.TokenService;
import com.uestc.crm.util.Result;
import com.uestc.crm.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:46
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        Gson gson = new Gson();
        ServletUtils.renderString(response, gson.toJson(Result.success("退出成功")));
    }
}
