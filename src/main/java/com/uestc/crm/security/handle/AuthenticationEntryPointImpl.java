package com.uestc.crm.security.handle;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:46
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = StrUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(Result.error(ExceptionCodeEnum.ERROR, msg)));
    }
}
