package com.fariga.filter;

import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;

@Log4j
@WebFilter(urlPatterns = {"/user/"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        BasicConfigurator.configure(); //this line helps to initialize the log4j system properly
        log.info("AccessFilter: Init method");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("UserRole");
        if (uri.endsWith("/user/login/") && ((userRole.equals("Admin")) | (userRole.equals("User")))) {
            filterChain.doFilter(request, response);
        } else {
            // pass the request along the filter chain
            request.getRequestDispatcher("/WEB-INF/user/login/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        log.info("AccessFilter: destroy method");
    }
}
