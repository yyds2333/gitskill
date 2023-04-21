package com.powernode.dao;


import com.powernode.utils.PageInfo;

import java.util.List;

public interface BaseDao {
    /**
     * 通过BaseDao接口来定义数据库的基础操作
     */
    /**
     * 一个修改操作的基础类
     * @param sql 修改的sql语句
     * @param params 修改的条件
     * @return
     */
    public int update(String sql,Object ...params);

    /**
     * 一个多项查询的基础操作
     * @param sql 查询语句
     * @param clss 接受类的类型
     * @param params 查询条件
     * @return
     * @param <T>
     */
    public <T> List<T> selectList(String sql,Class<T> clss,Object ...params);

    /**
     * 一个单项查询的基础操作
     * @param sql
     * @param clss
     * @param params
     * @return
     * @param <T>
     */
    public <T> T selectOne(String sql,Class<T> clss,Object ...params);

    /**
     * 一个根据id删除数据的操作
     * @param sql
     * @param params
     * @return
     */
    public int deleteOne(String sql,Object ...params);

    /**
     * 一个删除全部数据的操作
     * @param sql
     * @param params
     * @return
     */
    public int[] deleteAll(String sql,Object ...params);

    /**
     * 一个用来盛放分页查询结果的类
     * @param sql  查询语句
     * @param pageColumn  页码
     * @param pageSize     页面容量
     * @param clss      实体类型
     * @param params    条件
     * @return
     * @param <T>
     */
    public <T> PageInfo<T> selectListPage(String sql,int pageColumn,int pageSize,Class<T> clss,Object ...params);

    /**
     * 获取总共多少条数据
     * @return
     */
    int selectItems(String sql,Object ...params);
}