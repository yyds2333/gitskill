package com.powernode.servlet;

import com.powernode.domain.Student;
import com.powernode.service.StudentService;
import com.powernode.service.impl.StudentServiceImpl;
import com.powernode.utils.PageInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/main/welcome")
public class WelcomeServlet extends HttpServlet {
    // 用来查询数据的类
    private StudentService ss = new StudentServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取cmd，确定前端操作   cmd={selectAll,selectOne,deleteOne,deleteAll,updateQuery,update,pageSearch}
        String command = req.getParameter("command");
        // 确定command是否为空或者是空格
        if (command != null && "".equals(command.trim())) {
            if (command.equals("selectAll")) {
                // 查询全部数据
                selectAll(req, resp);
            } else if (command.equals("selectOne")) {
                // 查询单个数据
                selectOne(req, resp);
            } else if (command.equals("deleteOne")) {
                // 删除单个数据
                deleteOne(req, resp);
            } else if (command.equals("deleteAll")) {
                // 删除一组数据
                deleteAll(req, resp);
            } else if (command.equals("updateQuery")) {
                // 获取一个数据并返回给前台
                updateQuery(req, resp);
            } else if (command.equals("update")) {
                // 更新一个数据
                update(req, resp);
            } else if (command.equals("pageSearch")) {
                // 分页查询，这个查询语句有三个参数
                pageSearch(req, resp);
            } else {
                // 如果不能匹配上以上字段，则令其回到开始界面
                req.getRequestDispatcher("/WEB-INF/welcome.html").forward(req, resp);
            }
        }
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        req.setAttribute("username",username);
        req.getRequestDispatcher("/WEB-INF/welcome.html").forward(req, resp);
    }

    /**
     * 查询全部数据并返回
     */
    private void selectAll(HttpServletRequest req, HttpServletResponse resp) {
        List<Student> students = ss.selectList();
        req.setAttribute("students", students);
        try {
            req.getRequestDispatcher("/web/studentList.html").forward(req, resp);
        } catch (Exception e) {
            System.err.println("查询全部数据方法转发失败！");
        }
    }

    /**
     * 查询单个数据
     */
    private void selectOne(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Student student = ss.selectOne(Long.valueOf(id));
        req.setAttribute("student", student);
        try {
            req.getRequestDispatcher("/web/student.html").forward(req, resp);
        } catch (Exception e) {
            System.err.println("查询单个数据转发失败");
        }
    }

    /**
     * 删除单个数据
     */
    private void deleteOne(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        int delete = ss.delete(Long.valueOf(id));
        req.setAttribute("delete", delete);
        try {
            req.getRequestDispatcher("/web/studentList.html").forward(req, resp);
        } catch (Exception e) {
            System.err.println("删除单个数据转发失败");
        }
    }

    /**
     * 删除一组数据
     */
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        String ids = req.getParameter("ids");
        String[] sid = ids.split(",");
        long[] idss = new long[sid.length];
        for (int i = 0; i < sid.length; i++) {
            idss[i]=Long.valueOf(sid[i]);
        }
        int[] ints = ss.deleteAll(idss);
        int sum = ints.length;
        for (int i:ints){
            if (i==0){
               sum--;
            }
        }
        try{
            req.setAttribute("sum",sum);
            req.getRequestDispatcher("/web/studentList.html").forward(req,resp);
        }catch (Exception e){
            System.err.println("删除一组数据操作转发失败");
        }
    }
    /**
     * 查询一个数据返回到修改页面
     */
    private void updateQuery(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("id");
        Student student = ss.selectOne(Long.valueOf(id));
        req.setAttribute("student",student);
        try{
            req.getRequestDispatcher("/web/update.html").forward(req,resp);
        }catch (Exception e){
            System.err.println("查询单个数据返回修改页面失败");
        }
    }
    /**
     * 修改数据并返回主界面
     */
    private void update(HttpServletRequest req, HttpServletResponse resp){
        Student student = new Student();
        // 设置学号
        student.setStuId(Long.valueOf(req.getParameter("stuId")));
        // 设置名称
        student.setStuName(req.getParameter("stuName"));
        // 设置性别
        student.setGender(req.getParameter("gender"));
        // 设置密码
        student.setPassword(req.getParameter("password"));
        // 设置班级编号
        student.setProjectId(Integer.valueOf(req.getParameter("projectId")));
        // 获取id
        long id = Long.valueOf(req.getParameter("id"));
        int update = ss.update(student, id);
        req.setAttribute("flag",true);
        try {
            req.getRequestDispatcher("/web/studentList.html").forward(req,resp);
        }catch (Exception e){
            System.err.println("修改数据转发失败");
        }
    }
    /**
     * 分页查询数据
     */
    private void pageSearch(HttpServletRequest req, HttpServletResponse resp){
        // 获取设置的页面容量
        String pageSize = req.getParameter("pageSize");
        // 获取设置的页数
        String pageColumn = req.getParameter("pageColumn");
        PageInfo<Student> ps = new PageInfo<>();
        ps.setPageSize(Integer.valueOf(pageSize));
        ps.setPagaColumn(Integer.valueOf(pageColumn));
        PageInfo<Student> studentPage = ss.selectPage(ps);
        // 将分页查询总页数传回前端
        req.setAttribute("totalCount",studentPage.getTotalCount());
        // 将分页查询容量传回前端
        req.setAttribute("pageSize",pageSize);
        // 将分页查询总条数传回前端
        req.setAttribute("totalNums",studentPage.getTotalNums());
        // 将分页查询当前页数传回前端
        req.setAttribute("pageColumn",studentPage.getPagaColumn());
        // 将分页查询结果传回前端
        req.setAttribute("students",studentPage.getList());
        try{
            req.getRequestDispatcher("/web/pageList.html").forward(req,resp);
        }catch (Exception e){
            System.err.println("分页查询转发失败");
        }

    }
}
