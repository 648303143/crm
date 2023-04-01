package com.uestc.crm.security.context;

import org.springframework.security.core.Authentication;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:46
 */
public class AuthenticationContextHolder {
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
