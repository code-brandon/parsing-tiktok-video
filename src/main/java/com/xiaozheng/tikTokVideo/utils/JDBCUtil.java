package com.xiaozheng.tikTokVideo.utils;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * What -- 这是一个封装数据库连接的对象
 * <br>
 * Describe -- 他有获取链接与关闭链接的方法
 * <br>
 *
 * @Package: com.ztt.day1
 * @ClassName: JDBCutil
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2021/4/1 15:24
 */
public class JDBCUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        // 使用类加载器 获取类目录下的文件 并返回该文件的流
        InputStream resource = JDBCUtil.class.getClassLoader().getResourceAsStream("com/xiaozheng/tikTokVideo/jdbc.properties");
        // 创建 Properties对象 该对象用于获取 .properties文件    中的值
        Properties properties = new Properties();
        try {
            // 加载.properties文件的流
            properties.load(resource);

            // 获取.properties文件中的数据
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            // 注册jdbc驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username, password);
    }

    /**
     * 关闭与数据库连接的通讯
     * @param rs 结果集
     * @param stmt 执行静态SQL语句
     * @param con 数据库的连接
     */
    public static void close(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}