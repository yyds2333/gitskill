package com.powernode.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/main/*")
public class LoginFilter extends HttpFilter {

    @Override
    public void destroy() {

    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("经过过滤器2转码");
        HttpSession session = req.getSession();
        Object username = session.getAttribute("username");
        String uri = req.getRequestURI();
        System.out.println("uri="+uri);
        if (uri.equals("/main/login")||username!=null){
            if (username!=null){
                System.out.println(username);
                chain.doFilter(req,res);
            }else{
                req.getRequestDispatcher("/main/login").forward(req,res);
            }
        }else{
            req.getRequestDispatcher("/login.html").forward(req,res);
        }
    }
}