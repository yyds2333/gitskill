package com.powernode.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 创建一个工具类
 */
public class DBUtils {
    /**
     * 使用druid数据库连接池来创造一个工具类
     */
    private static DataSource dataSource = null;
    private static DruidDataSource dds = null;
    private static Connection conn = null;
    private static Properties prop = new Properties();
    private static InputStream is = null;

    static {
        try{
            // 将配置文件读取到文件流中
            is = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(is);
            //加载配置文件
            prop.load(is);
            //根据配置文件创建数据库连接池
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("获取配置文件失败！");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取连接池失败！");
        }
    }

    /**
     * 创建一个获取Connection对象的方法
     * @return
     */
    public static Connection getConn(){
        try{
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("获取对象Conn失败!");
        }
        return conn;
    }

    /**
     * 创建一个方法，用来关闭对象
     */
    public static void close(Object ...params){
        for (int i = 0;i<params.length;i++){
            if (params[i]!=null){
                try{
                    //通过反射调取该类的close方法并使用该方法
                    params[i].getClass().getMethod("close").invoke(params[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("释放资源失败！");
                }
            }
        }
    }
}
