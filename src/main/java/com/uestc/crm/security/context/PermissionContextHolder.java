package com.uestc.crm.security.context;

import cn.hutool.core.convert.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:46
 */
public class PermissionContextHolder
{
    private static final String PERMISSION_CONTEXT_ATTRIBUTES = "PERMISSION_CONTEXT";

    public static void setContext(String permission)
    {
        RequestContextHolder.currentRequestAttributes().setAttribute(PERMISSION_CONTEXT_ATTRIBUTES, permission,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static String getContext()
    {
        return Convert.toStr(RequestContextHolder.currentRequestAttributes().getAttribute(PERMISSION_CONTEXT_ATTRIBUTES,
                RequestAttributes.SCOPE_REQUEST));
    }
}
