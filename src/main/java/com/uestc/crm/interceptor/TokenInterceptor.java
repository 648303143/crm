package com.uestc.crm.interceptor;

import cn.hutool.core.util.StrUtil;
import com.google.gson.JsonObject;
import com.uestc.crm.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/7 17:49
 */

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        response.setCharacterEncoding("utf-8");

        String token = request.getHeader("Token");

        if (StrUtil.isNotBlank(token)) {
            boolean result = TokenUtil.verify(token);
            if (result) {
                System.out.println("通过拦截器");
                return true;
            }else {
                System.out.println("error :: token验证不通过");
            }
        }else {
            System.out.println("error :: token为空");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JsonObject json = new JsonObject();
            json.addProperty("success", "false");
            json.addProperty("msg", "认证失败，未通过拦截器");
            json.addProperty("code", 50008);
            response.getWriter().append(json.toString());
            System.out.println("认证失败，未通过拦截器");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(50008);
            return false;
        }
        return false;

    }
}

