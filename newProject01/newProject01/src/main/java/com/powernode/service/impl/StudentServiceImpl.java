package com.powernode.service.impl;

import com.powernode.dao.BaseDao;
import com.powernode.dao.impl.BaseDaoImpl;
import com.powernode.domain.Student;
import com.powernode.service.StudentService;
import com.powernode.utils.PageInfo;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private BaseDao bd = new BaseDaoImpl();

    @Override
    public int insert(Student student) {
        String sql = "insert into student_tbl values(null,?,?,?,?,?)";
        long stuId = student.getStuId();
        String stuName = student.getStuName();
        String gender = student.getGender();
        String password = student.getPassword();
        int projectId = student.getProjectId();
        int update = bd.update(sql, stuId, stuName, gender, password, projectId);
        return update;
    }


    @Override
    public int update(Student student, long id) {
        String sql = "update student_tbl set id = id ,stuId = ?,stuName = ?,gender = ?,password = ?,projectId = ? where id = ?";
        long stuId = student.getStuId();
        String stuName = student.getStuName();
        String gender = student.getGender();
        String password = student.getPassword();
        int projectId = student.getProjectId();
        int update = bd.update(sql, stuId, stuName, gender, password, projectId, id);
        return update;
    }


    @Override
    public int delete(long id) {
        String sql = "delete from student_tbl where id = ?";
        int i = bd.deleteOne(sql, id);
        return i;
    }


    @Override
    public int[] deleteAll(Object... params) {
        String sql = "delete from student_tbl where id = ?";
        int[] ints = bd.deleteAll(sql, params);
        return ints;
    }

    /**
     * 根据学号或id查询某个学生
     *
     * @param other
     * @return
     */
    @Override
    public Student selectOne(Object other) {
        Student student = null;
        String sOther = String.valueOf(other);
        if (sOther.length() > 5 && sOther.startsWith("10")) {
            String sql = "select * from student_tbl where stuId = ?";
            student = bd.selectOne(sql, Student.class, other);
        } else {
            String sql = "select * from student_tbl where id = ?";
            student = bd.selectOne(sql, Student.class, other);
        }
        return student;
    }

    @Override
    public List<Student> selectList() {
        List<Student> list = new ArrayList<>();
        String sql = "select * from student_tbl";
        list = bd.selectList(sql, Student.class);
        return list;
    }

    @Override
    public PageInfo<Student> selectPage(PageInfo pageInfo) {
        String sql = "select * from student_tbl";
        int pageColumn = pageInfo.getPagaColumn();
        int pageSize = pageInfo.getPageSize();
        PageInfo<Student> studentPageInfo = bd.selectListPage(sql, pageColumn, pageSize, Student.class);
        return studentPageInfo;
    }
}
