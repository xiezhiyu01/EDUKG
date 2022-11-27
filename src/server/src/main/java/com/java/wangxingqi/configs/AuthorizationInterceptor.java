package com.java.wangxingqi.configs;

import com.java.wangxingqi.utils.JsonResponse;
import com.java.wangxingqi.utils.JsonUtil;
import com.java.wangxingqi.utils.JwtUtil;
import com.java.wangxingqi.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String token = request.getHeader("token");
            if (token != null && token.length() > 0) {
                Claims c = JwtUtil.parseJWT(token);
                request.setAttribute("userId", c.get(JwtUtil.keyUserId, Long.class));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonResponse responseInfo = new JsonResponse(ResponseUtil.FORBIDDEN.getStatus(), ResponseUtil.FORBIDDEN.getMessage());
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String json = JsonUtil.toJsonString(responseInfo);
            PrintWriter out = response.getWriter();
            out.append(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
