package com.qwk.dao;


import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class JDBC {

	private static final String DRIVERCLASS = "com.mysql.jdbc.Driver"; // 数据库驱动
	private static final String url = "jdbc:mysql://localhost:3306/test";// 数据库URL
	
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); // 创建用来保存数据库连接的线程
    private static String username = "root";
    private static String password = "123456";
    static Connection conn = null;
    public PreparedStatement pst = null;
    
    
    static { // 通过静态方法加载数据库驱动，并且在数据库不存在的情况下创建数据库
        System.out.println("JDBC");
        try {
            Class.forName(DRIVERCLASS); // 加载数据库驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
            threadLocal.set(conn);// 保存数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }     
    
    protected static Connection getConnection() { // 创建数据库连接的方法

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        if (conn == null) { // 没有可用的数据库连接
        	System.out.println("没有可用的数据库连接");
        }
        return conn;
    }
    
    protected static boolean closeConnection() { // 关闭数据库连接的方法

        boolean isClosed = true; // 默认关闭成功

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        threadLocal.set(null); // 清空线程中的数据库连接

        if (conn != null) { // 数据库连接可用

            try {
                conn.close(); // 关闭数据库连接

            } catch (SQLException e) {
                isClosed = false; // 关闭失败

                e.printStackTrace();
            }
        }
        return isClosed;
    }
    
}
