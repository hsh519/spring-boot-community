package com.example.blog.interceptor;


import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("call");
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        log.info("requestURI = {}", requestURI);
        if (session == null || session.getAttribute("loginMember") == null) {
            response.sendRedirect("/login?requestURI=" + requestURI);
            return false;
        }
        return true;
    }
}
