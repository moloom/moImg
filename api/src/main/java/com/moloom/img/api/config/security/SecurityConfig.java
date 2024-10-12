package com.moloom.img.api.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author: moloom
 * @date: 2024-10-12 00:16
 * @description: config of security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @return
     * @author
     * @date 2024-10-12 01:45:01
     * @description 对密码进行加密
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return
     * @author
     * @date 2024-10-12 01:45:01
     * @description 配置过滤器链
     **/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> {
                    auth.anyRequest().permitAll();
//                    auth.requestMatchers("/i/test", "/upload/").permitAll();
                    //把剩下的请求都拦截，需要登录
//                    auth.anyRequest().authenticated();
                })
                .formLogin(conf -> {
                    conf.loginPage("/login"); // 自定义登录页面
                    conf.loginProcessingUrl("/login");    // 自定义登录处理路径
                    conf.defaultSuccessUrl("https://www.baidu.com");    // 登录成功后跳转路径
                    conf.permitAll();   // 允许所有用户登录
                    conf.usernameParameter("user");     // 自定义用户名参数
                    conf.passwordParameter("password"); // 自定义密码参数
                })
                .logout(conf -> {
                    conf.logoutUrl("doLogout");
                    //conf.logoutSuccessUrl("/");
                    conf.permitAll();
                })
                .rememberMe(conf -> {
                    //不用这个，我需要自定义一个token，未登录用户在浏览器里都会生成一个。
                    //conf.alwaysRemember(false);     // 记住我默认为false
                    conf.rememberMeParameter("remember-me");    // 自定义记住我参数,type=checkbox
                    conf.rememberMeCookieName("xxx");   // 自定义cookie名称
                })
                .csrf(conf -> conf.disable())   // 关闭csrf
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.withUsername("moloom")
                .password(encoder.encode("mo"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);

    }
}
