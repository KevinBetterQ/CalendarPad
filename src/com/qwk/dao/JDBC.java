package com.qwk.dao;


import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class JDBC {

	private static final String DRIVERCLASS = "com.mysql.jdbc.Driver"; // ���ݿ�����
	private static final String url = "jdbc:mysql://localhost:3306/test";// ���ݿ�URL
	
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); // ���������������ݿ����ӵ��߳�
    private static String username = "root";
    private static String password = "123456";
    static Connection conn = null;
    public PreparedStatement pst = null;
    
    
    static { // ͨ����̬�����������ݿ����������������ݿⲻ���ڵ�����´������ݿ�
        System.out.println("JDBC");
        try {
            Class.forName(DRIVERCLASS); // �������ݿ�����
            conn = (Connection) DriverManager.getConnection(url, username, password);
            threadLocal.set(conn);// �������ݿ�����
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }     
    
    protected static Connection getConnection() { // �������ݿ����ӵķ���

        conn = (Connection) threadLocal.get(); // ���߳��л�����ݿ�����

        if (conn == null) { // û�п��õ����ݿ�����
        	System.out.println("û�п��õ����ݿ�����");
        }
        return conn;
    }
    
    protected static boolean closeConnection() { // �ر����ݿ����ӵķ���

        boolean isClosed = true; // Ĭ�Ϲرճɹ�

        conn = (Connection) threadLocal.get(); // ���߳��л�����ݿ�����

        threadLocal.set(null); // ����߳��е����ݿ�����

        if (conn != null) { // ���ݿ����ӿ���

            try {
                conn.close(); // �ر����ݿ�����

            } catch (SQLException e) {
                isClosed = false; // �ر�ʧ��

                e.printStackTrace();
            }
        }
        return isClosed;
    }
    
}
