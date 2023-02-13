package com.chenfj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/2 15:09
 * @Description:
 * @version: 1.0
 */
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 设置默认首页
        registry.addViewController("/").setViewName("login.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许请求的方式
                .allowedMethods("GET","POST","DELETE","PUT")
                // 设置允许的header 属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
