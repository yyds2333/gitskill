package com.powernode.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.domain.Student;
import com.powernode.service.StudentService;
import com.powernode.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {
    private static StudentService ss = new StudentServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stuId = req.getParameter("stuId");
        System.out.println("stuId="+stuId);
        Student student = ss.selectOne(stuId);
        System.out.println("student="+student);
        // 设置一个响应类型
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        // 创建一个集合用来设置
        Map<String,Object> omaper = new HashMap<>();
        if (student != null && student.getStuId() == Long.valueOf(stuId)){
            System.out.println("已存在");
            omaper.put("flag",true);
            omaper.put("msg","x");
        }else{
            System.out.println("不存在");
            omaper.put("flag",false);
            omaper.put("msg","√");
        }
        ObjectMapper om = new ObjectMapper();
        String js = om.writeValueAsString(omaper);
        writer.print(js);
    }
}
