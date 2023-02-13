package com.chenfj.security;

import com.chenfj.security.exception.AccessDeniedHandlerImpl;
import com.chenfj.security.exception.AuthenticationEntryPointImpl;
import com.chenfj.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 16:30
 * @Description:
 * @version: 1.0
 */
//prePostEnabled属性决定Spring Security在接口前注解是否可用@PreAuthorize,@PostAuthorize等注解,设置为true,会拦截加了这些注解的接口
//@EnableGlobalMethodSecurity(/*prePostEnabled = true,*/ securedEnabled = true)

@EnableWebSecurity//开启Spring Security的功能
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable()
                // 不通过session 获取securityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //1、配置权限认证
                .authorizeRequests()
                .antMatchers("/user/login")
                //对于登录接口 允许匿名访问
                .anonymous()
                //除了上面外的所有全部请求都要鉴权认证
                .anyRequest().authenticated();
        /*http.logout(logout -> logout.logoutUrl("").deleteCookies("JSESSIONID")
                .logoutSuccessHandler());*/
        // 把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置异常处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        // 允许跨域
        //http.csrf();


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/url");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
