package com.gzu;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 使用 @WebFilter 注解配置过滤器，使其应用于所有 URL 路径 ("/*")
@WebFilter("/*")
public class LoginFilter implements Filter {

    // 定义一个排除列表，包含不需要登录就能访问的路径
    private static final String[] EXCLUDED_PATHS = {"/login", "/register", "/public"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化代码（如果有的话）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 检查当前请求路径是否在排除列表中
        if (isExcludedPath(httpRequest.getRequestURI())) {
            chain.doFilter(request, response); // 如果是排除路径，则直接通过
        } else {
            HttpSession session = httpRequest.getSession(false); // 获取session，不创建新session

            // 检查session中是否存在表示已登录的属性
            if (session != null && session.getAttribute("user") != null) {
                chain.doFilter(request, response); // 用户已登录，继续处理请求
            } else {
                // 用户未登录，重定向到登录页面
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        }
    }

    @Override
    public void destroy() {
        // 过滤器销毁代码（如果有的话）
    }

    // 检查当前请求路径是否在排除列表中
    private boolean isExcludedPath(String requestURI) {
        for (String path : EXCLUDED_PATHS) {
            if (requestURI.endsWith(path)) {
                return true;
            }
        }
        return false;
    }
}