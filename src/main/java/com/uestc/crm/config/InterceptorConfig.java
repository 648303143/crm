package com.uestc.crm.config;

import com.uestc.crm.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/7 18:14
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private TokenInterceptor tokenInterceptor;

    //构造方法
    public InterceptorConfig(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        //登录
        excludePath.add("/user/login");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        //除了登陆接口其他所有接口都需要token验证
        WebMvcConfigurer.super.addInterceptors(registry);

    }

}
