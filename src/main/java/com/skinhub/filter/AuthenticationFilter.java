package com.skinhub.filter;

import java.io.IOException;

import com.skinhub.util.CookieUtil;
import com.skinhub.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    private static final String DASHBOARD = "/adminDashboard";
    private static final String ABOUT = "/aboutus";
    private static final String CONTACT = "/contactus";
    private static final String PORTFOLIO = "/portfolio";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // Allow static resources
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        String role = (String) SessionUtil.getAttribute(req, "role");
        boolean isLoggedIn = role != null;

        if (isLoggedIn) {
            if ("ADMIN".equalsIgnoreCase(role)) {
                // Admin can access admin pages, block customer-only pages
                if (uri.contains("/adminDashboard") || uri.contains("/logout") || uri.contains("/portfolio")) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect(req.getContextPath() + "/adminDashboard");
                }
            } else if ("CUSTOMER".equalsIgnoreCase(role)) {
                // Customer can access home, profile, etc.
                if (uri.contains("/home") || uri.contains("/portfolio") || uri.contains("/logout") || uri.contains("/aboutus") || uri.contains("/contactus")) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect(req.getContextPath() + "/home");
                }
            } else {
                // Unknown role, force logout
                SessionUtil.invalidateSession(req);
                res.sendRedirect(req.getContextPath() + "/login");
            }
        } else {
            // Not logged in: allow only login, register, home, about, contact
            if (uri.contains("/login") || uri.contains("/register") || uri.contains("/home") || uri.contains("/aboutus") || uri.contains("/contactus") || uri.equals(req.getContextPath() + "/")) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
        }
    
    }

    @Override
    public void destroy() {
        // Cleanup logic, if required
    }
}