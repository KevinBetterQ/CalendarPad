/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qwk.tool;

import com.qwk.dao.Dao;
import com.qwk.frame.SeeNoteDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


//负责维护定时提醒线程
public class AwakeThread extends Thread {

    private static final Dao DAO = Dao.getInstance();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static AwakeThread awakeThread = new AwakeThread();
    private static Vector<Vector> awakeNotes;// 需要提醒的记录
    private static Date awakeTime;// 提醒时间
    AwakeThread awakeThread2;

    private AwakeThread() {
    }

@Override
public void run() {
    while (true) {
        Date nowTime = Calendar.getInstance().getTime();// 获得当前时间
        if (nowTime.compareTo(awakeTime) >= 0) {// 到了提醒时间
            final int w = 420,  h = 373;// 定义提示框大小
            int maxIndex = awakeNotes.size() - 1;// 获得最大索引
            for (int i = maxIndex; i >= 0; i--) {// 遍历提示记录
                final Vector awakeNote = awakeNotes.get(i);// 获得提示记录
                final int offset = maxIndex * 15 - i * 30;// 计算偏移量
                final int x = 335 - offset,  y = 197 - offset;// 定义提示框的起始绘制位置
                new Thread() {// 创建并开启一个线程

                    @Override
                    public void run() {// 重构该方法
                        SeeNoteDialog dialog = new SeeNoteDialog(null, true);// 创建提示框
                        dialog.setSeeNote(awakeNote);// 设置提示记录
                        dialog.setAlwaysOnTop(true);// 永远在最前面
                        dialog.setBounds(x, y, w, h);// 设置提示框的绘制位置
                        dialog.setVisible(true);// 显示提示框
                    }
                }.start();
            }
            try {
                Thread.sleep(1000);// 休眠1秒
            } catch (InterruptedException ex) {
            
            	Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            awakeThread.interrupt();// 终止线程
            /*System.out.println("11");
            //refresh();// 提醒记录
            awakeThread.interrupt();// 终止线程
            System.out.println("22");*/
        }
        if (awakeThread.isInterrupted()) {// 线程已终止
        	System.out.println("跳出循环");
        	/*awakeThread2 = new AwakeThread();
        	//awakeThread = AwakeThread.getInstance();
            if (awakeThread2.isAlive()) {
                awakeThread2.refresh();
            } else {
                    awakeThread2.turnOn();
            }*/
            break;// 跳出循环
        }
        try {
            Thread.sleep(1000);// 休眠1秒
        } catch (InterruptedException ex) {
            Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    public void refresh() {
        awakeNotes = DAO.sAwakeOfLatest();// 获得最近需要提醒的记录

        if (awakeNotes == null) {// 没有需要提醒的记录

            synchronized (awakeThread) {// 将线程加入同步块

                awakeThread.interrupt();// 终止线程

            }
        } else {// 存在需要提醒的记录

            try {
                awakeTime = DATE_FORMAT.parse(
                        awakeNotes.get(0).get(2) + " " + awakeNotes.get(0).get(3));// 解析提醒时间

            } catch (ParseException ex) {
                Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("refresh");
    }

    public void turnOn() {
        awakeNotes = DAO.sAwakeOfLatest();// 获得最近需要提醒的记录

        if (awakeNotes != null) {// 存在需要提醒的记录

            try {
                awakeTime = DATE_FORMAT.parse(
                        awakeNotes.get(0).get(2) + " " + awakeNotes.get(0).get(3));// 解析提醒时间

            } catch (ParseException ex) {
                Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            awakeThread = new AwakeThread();// 创建线程对象

            awakeThread.start();// 开启线程

        }
        System.out.println("turnon");
    }

    public static AwakeThread getInstance() {
        return awakeThread;
    }
}
