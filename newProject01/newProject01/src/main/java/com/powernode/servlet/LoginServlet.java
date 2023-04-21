package com.powernode.servlet;

import com.powernode.domain.Student;
import com.powernode.service.StudentService;
import com.powernode.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/main/login")
public class LoginServlet extends HttpServlet {
    StudentService ss = new StudentServiceImpl();

    /**
     * 这个请求用来判断登录状态
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String stuId = req.getParameter("stuId");
        // 获取前台传来的stuId
        System.out.println("stuid=" + stuId);
        String password = req.getParameter("password");
        // 获取前台传来的password
        System.out.println("password=" + password);
        Student student = ss.selectOne(stuId);
        System.out.println("student="+student);
        long userId = student.getStuId();
        System.out.println("userId=" + userId);
        String pass = student.getPassword();
        String username = student.getStuName();
        System.out.println("username="+username);
        // 如果username存在于数据库中，则比对password是否相同，相同的话赋予session一个权限
        if (userId > 0) {
            System.out.println(String.valueOf(userId).equals(stuId));
            if (String.valueOf(userId).equals(stuId) && pass.equals(password)) {
                System.out.println("设置username");
                session.setAttribute("username", username);
                req.setAttribute("username", username);
                req.getRequestDispatcher("/main/welcome").forward(req, resp);
            }else {
                req.getRequestDispatcher("/login.html").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/login.html").forward(req, resp);
        }
    }
}
