package com.linlin.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  令牌验证
        String token = request.getHeader("Authorization");
        //  验证token
        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//            System.out.println(claims);
            //  放行
            return true;
        } catch (Exception e) {
            //  http响应状态码为401
            response.setStatus(401);
            //  不放行
            return false;
        }

    }
}
