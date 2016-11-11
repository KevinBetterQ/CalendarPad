package com.qwk.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class BaseDao {

    // ��ѯ�����¼
    public Vector selectSomeNote(String sql) {
        Vector<Vector<Object>> rsV = new Vector<Vector<Object>>();// �������������

        Connection conn = JDBC.getConnection();// ������ݿ�����

        try {
            Statement stmt = conn.createStatement();// ��������״̬����

            ResultSet rs = stmt.executeQuery(sql);// ִ��SQL����ò�ѯ���

            int columnCount = rs.getMetaData().getColumnCount();// ��ò�ѯ���ݱ������

            while (rs.next()) {// ���������

                Vector<Object> rowV = new Vector<Object>();// ����������

                for (int column = 1; column <= columnCount; column++) {
                    rowV.add(rs.getObject(column));// �����ֵ

                }
                rsV.add(rowV);// ����������ӵ������������

            }
            rs.close();// �رս��������

            stmt.close();// �ر�����״̬����

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsV;// ���ؽ��������

    }

    // ��ѯ������¼
    public Vector selectOnlyNote(String sql) {
        Vector<Object> rowV = null;
        Connection conn = JDBC.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                rowV = new Vector<Object>();
                for (int column = 1; column <= columnCount; column++) {
                    rowV.add(rs.getObject(column));
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowV;
    }

    // ��ѯ���ֵ
    public Vector selectSomeValue(String sql) {
        Vector<Object> valueV = new Vector<Object>();
        Connection conn = JDBC.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                valueV.add(rs.getObject(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valueV;
    }

    // ��ѯ����ֵ
    public Object selectOnlyValue(String sql) {
        Object value = null;
        Connection conn = JDBC.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                value = rs.getObject(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    // ���롢�޸ġ�ɾ����¼
    public boolean longHaul(String sql) {
        boolean isLongHaul = true;// Ĭ�ϳ־û��ɹ�

        Connection conn = JDBC.getConnection();// ������ݿ�����

        try {
            conn.setAutoCommit(false);// ����Ϊ�ֶ��ύ

            Statement stmt = conn.createStatement();// ��������״̬����

            stmt.executeUpdate(sql);// ִ��SQL���

            stmt.close();// �ر�����״̬����

            conn.commit();// �ύ�־û�

        } catch (SQLException e) {
            isLongHaul = false;// �־û�ʧ��

            try {
                conn.rollback();// �ع�

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return isLongHaul;// ���س־û����

    }
}
