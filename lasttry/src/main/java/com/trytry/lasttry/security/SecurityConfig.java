package com.trytry.lasttry.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 关闭 CSRF 保护（针对 REST API）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/hello1",
                                "/api/login",
                                "/api/register",
                                "/api/interest-tags",
                                "/api/diaries/{diary_id}",
                                "/api/diaries/search",
                                "/api/diaries",
                                "/api/diaries/title",
                                "/api/test/sync"
                        ).permitAll() // 放行登录和注册
                        .anyRequest().authenticated() // 其他请求需要认证
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 关闭 session，使用 JWT 认证
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器

        return http.build();
    }

    // 需要 AuthenticationManager 来支持身份验证
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
