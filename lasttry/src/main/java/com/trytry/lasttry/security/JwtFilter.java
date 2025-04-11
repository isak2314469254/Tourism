package com.trytry.lasttry.security;

import com.trytry.lasttry.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain
    )
            throws ServletException, IOException {

        String path = request.getRequestURI();

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String username = JwtUtil.verifyToken(token.substring(7));

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                //创建一个空角色，默认没有权限
                UserDetails userDetails = new User(
                        username, "", Collections.emptyList()
                );
                //创建一个认证成功的对象，表示这个请求已经通过身份验证。
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //将认证成功的对象设置到上下文中，这样Spring Security就可以知道这个请求已经通过身份验证。
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //放行请求，进入下一个过滤器或最终处理的 Controller。
        chain.doFilter(request, response);
    }
}
