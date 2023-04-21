package com.powernode.service;

import com.powernode.domain.Student;
import com.powernode.utils.PageInfo;

import java.util.List;

public interface StudentService {
    /**
     * 插入新数据
     * @param student
     * @return
     */
    int insert(Student student);

    /**
     * 修改某个数据根据id
     * @param student
     * @param id
     * @return
     */
    int update(Student student,long id);

    /**
     * 删除某个数据给根据id
     * @param id
     * @return
     */
    int delete(long id);

    /**
     * 删除多个元素，根据id数组
     * @param params
     * @return
     */
    int[] deleteAll(Object ...params);

    /**
     * 根据id或学号查询某个值
     * @param other
     * @return
     */
    Student selectOne(Object other);

    /**
     * 获取全部数据
     * @return
     */
    List<Student> selectList();

    /**
     * 分页获取数据
     * @return
     */
    PageInfo<Student> selectPage(PageInfo pageInfo);

}
