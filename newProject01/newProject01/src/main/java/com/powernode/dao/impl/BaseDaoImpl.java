package com.powernode.dao.impl;

import com.powernode.dao.BaseDao;
import com.powernode.utils.DBUtils;
import com.powernode.utils.PageInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class BaseDaoImpl implements BaseDao {
    public BaseDaoImpl() {
    }

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    /**
     * @param sql    修改的sql语句
     * @param params 修改的条件
     * @return
     */
    @Override
    public int update(String sql, Object... params) {
        //如果上一层传进来的语句不存在，则返回0
        if (sql == null || "".equals(sql.trim())) return 0;
        //获取连接
        conn = DBUtils.getConn();
        int s = 0;
        try {
            //获得预编译SQL语句
            ps = conn.prepareCall(sql);
            for (int i = 0; params != null && params.length >= 1 && i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            //s用来接收查询的结果影响行数
            s = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            DBUtils.close(ps, conn);
        }
        return s;
    }

    /**
     * @param sql    查询语句
     * @param clss   接受类的类型
     * @param params 查询条件
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> selectList(String sql, Class<T> clss, Object... params) {
        List<T> list = new ArrayList<>();
        //如果上一层传进来的语句不存在，则返回空集合
        if (sql == null || "".equals(sql.trim())) return null;
        conn = DBUtils.getConn();
        try {
            ps = conn.prepareCall(sql);
            for (int i = 0; params != null && params.length >= 1 && i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            //rs接收查询到的结果集
            rs = ps.executeQuery();
            //用ResultMetaData赋值
            ResultSetMetaData metaData = rs.getMetaData();
            //获取属性行数
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                //通过传递进来的对象类型创建一个新对象,用来接收rs的数据
                T t = clss.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    //ResultSetMetaData中的数据下标从1开始
                    //接收metaData中的列名
                    String columnName = metaData.getColumnName(i);
                    //Object对象接收rs中的数据
                    Object val = rs.getObject(i);
                    //通过反射将值赋给实体类
                    Field field = t.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, val);
                }
                //将对象添加到集合
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            DBUtils.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 根据id获取单个对象
     *
     * @param sql
     * @param clss
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T selectOne(String sql, Class<T> clss, Object... params) {
        T t = null;
        if (sql == null || "".equals(sql.trim())) return t;
        // 获取连接
        conn = DBUtils.getConn();
        try {
            //创建一个对象
            t = clss.getConstructor().newInstance();
            // 创建查询对象
            ps = conn.prepareCall(sql);
            // 添加查询信息
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            // rs接收查询结果
            rs = ps.executeQuery();
            // 获取ResultSetMetadata查询结果的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 取得查询结果的列数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    //根据column来获取rs中的数据
                    String columnName = rsmd.getColumnName(i);
                    //根据列名获取属性值
                    Object value = rs.getObject(columnName);
                    // 根据列名获取属性名
                    Field field = t.getClass().getDeclaredField(columnName);
                    // 将属性设置为可修改状态
                    field.setAccessible(true);
                    //设置对象的属性
                    field.set(t, value);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放数据库资源
            DBUtils.close(rs, ps, conn);
        }
        return t;
    }

    @Override
    public int deleteOne(String sql, Object ...params) {
        int sum = 0;
        conn = DBUtils.getConn();
        try{
            ps = conn.prepareCall(sql);
            // 添加查询信息
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            sum = ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
        return sum;
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    @Override
    public int[] deleteAll(String sql, Object... params) {
        int[] ints = null;
        if (sql == null || "".equals(sql.trim())) return null;
        // 获取一个连接对象
        conn = DBUtils.getConn();
        try {
            // 获取预编译sql执行对象
            ps = conn.prepareCall(sql);
            // 打开数据库的事务支持
            conn.setAutoCommit(false);
            // 判断params中是否存在数据
            if (params != null || params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(1, params[i]);
                    ps.addBatch();
                }
                ints = ps.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 提交事务
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtils.close(ps, conn);
        }
        return (ints != null ? ints : null);
    }

    /**
     * @param sql        查询语句
     * @param pageColumn 页码
     * @param pageSize   页面容量
     * @param clss       实体类型
     * @param params     条件
     * @param <T>
     * @return
     */
    @Override
    public <T> PageInfo<T> selectListPage(String sql, int pageColumn, int pageSize, Class<T> clss, Object... params) {
        PageInfo<T> pageInfo = new PageInfo<>();
        List<T> list = new ArrayList<>();
        if (sql == null || "".equals(sql.trim())) return pageInfo;
        // 获取连接
        conn = DBUtils.getConn();
        // 将sql转换为分页查询
        String s1 = sql.replaceFirst(";", "");
        String nSql = s1 + " limit " + (pageColumn-1)*pageSize +","+pageSize;
        try{
            // 获取预编译语句
            ps = conn.prepareCall(nSql);
            // 添加参数
            if (params!=null&&params.length>0){
                for (int i = 1;i<=params.length;i++){
                    ps.setObject(i,params[i-1]);
                }
            }
            // 获取查询结果
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()){
                // 新建一个对象
                T t = clss.getDeclaredConstructor().newInstance();
                for (int i = 1;i<=columnCount;i++){
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(columnName);
                    Field f = t.getClass().getDeclaredField(columnName);
                    f.setAccessible(true);
                    f.set(t,value);
                }
                list.add(t);
            }
            pageInfo.setList(list);
            pageInfo.setPageSize(pageSize);
            pageInfo.setPagaColumn(pageColumn);
            pageInfo.setTotalNums(selectItems("select count(*) "+s1.substring(s1.indexOf("from")),params));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * @param sql
     * @return
     */
    @Override
    public int selectItems(String sql,Object ...params) {
        int sum = 0;
        if (sql == null || "".equals(sql.trim())) return sum;
        // 获取连接
        conn = DBUtils.getConn();
        try {
            ps = conn.prepareCall(sql);
            for (int i = 0; params!=null&&params.length>0&&i<params.length;i++){
                ps.setObject(i+1,params[i]);
            }
            rs = ps.executeQuery();
            while(rs.next()){
                sum = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs,ps, conn);
        }
        return sum;
    }
}
