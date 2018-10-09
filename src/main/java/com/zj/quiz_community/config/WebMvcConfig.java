package com.zj.quiz_community.config;

import com.zj.quiz_community.interceptor.LoginRequiredInterceptor;
import com.zj.quiz_community.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhaojie
 * @date 2018\10\8 0008 - 23:44
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");

        super.addInterceptors(registry);
    }
}
