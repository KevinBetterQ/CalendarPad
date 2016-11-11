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


//����ά����ʱ�����߳�
public class AwakeThread extends Thread {

    private static final Dao DAO = Dao.getInstance();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static AwakeThread awakeThread = new AwakeThread();
    private static Vector<Vector> awakeNotes;// ��Ҫ���ѵļ�¼
    private static Date awakeTime;// ����ʱ��
    AwakeThread awakeThread2;

    private AwakeThread() {
    }

@Override
public void run() {
    while (true) {
        Date nowTime = Calendar.getInstance().getTime();// ��õ�ǰʱ��
        if (nowTime.compareTo(awakeTime) >= 0) {// ��������ʱ��
            final int w = 420,  h = 373;// ������ʾ���С
            int maxIndex = awakeNotes.size() - 1;// ����������
            for (int i = maxIndex; i >= 0; i--) {// ������ʾ��¼
                final Vector awakeNote = awakeNotes.get(i);// �����ʾ��¼
                final int offset = maxIndex * 15 - i * 30;// ����ƫ����
                final int x = 335 - offset,  y = 197 - offset;// ������ʾ�����ʼ����λ��
                new Thread() {// ����������һ���߳�

                    @Override
                    public void run() {// �ع��÷���
                        SeeNoteDialog dialog = new SeeNoteDialog(null, true);// ������ʾ��
                        dialog.setSeeNote(awakeNote);// ������ʾ��¼
                        dialog.setAlwaysOnTop(true);// ��Զ����ǰ��
                        dialog.setBounds(x, y, w, h);// ������ʾ��Ļ���λ��
                        dialog.setVisible(true);// ��ʾ��ʾ��
                    }
                }.start();
            }
            try {
                Thread.sleep(1000);// ����1��
            } catch (InterruptedException ex) {
            
            	Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            awakeThread.interrupt();// ��ֹ�߳�
            /*System.out.println("11");
            //refresh();// ���Ѽ�¼
            awakeThread.interrupt();// ��ֹ�߳�
            System.out.println("22");*/
        }
        if (awakeThread.isInterrupted()) {// �߳�����ֹ
        	System.out.println("����ѭ��");
        	/*awakeThread2 = new AwakeThread();
        	//awakeThread = AwakeThread.getInstance();
            if (awakeThread2.isAlive()) {
                awakeThread2.refresh();
            } else {
                    awakeThread2.turnOn();
            }*/
            break;// ����ѭ��
        }
        try {
            Thread.sleep(1000);// ����1��
        } catch (InterruptedException ex) {
            Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    public void refresh() {
        awakeNotes = DAO.sAwakeOfLatest();// ��������Ҫ���ѵļ�¼

        if (awakeNotes == null) {// û����Ҫ���ѵļ�¼

            synchronized (awakeThread) {// ���̼߳���ͬ����

                awakeThread.interrupt();// ��ֹ�߳�

            }
        } else {// ������Ҫ���ѵļ�¼

            try {
                awakeTime = DATE_FORMAT.parse(
                        awakeNotes.get(0).get(2) + " " + awakeNotes.get(0).get(3));// ��������ʱ��

            } catch (ParseException ex) {
                Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("refresh");
    }

    public void turnOn() {
        awakeNotes = DAO.sAwakeOfLatest();// ��������Ҫ���ѵļ�¼

        if (awakeNotes != null) {// ������Ҫ���ѵļ�¼

            try {
                awakeTime = DATE_FORMAT.parse(
                        awakeNotes.get(0).get(2) + " " + awakeNotes.get(0).get(3));// ��������ʱ��

            } catch (ParseException ex) {
                Logger.getLogger(AwakeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            awakeThread = new AwakeThread();// �����̶߳���

            awakeThread.start();// �����߳�

        }
        System.out.println("turnon");
    }

    public static AwakeThread getInstance() {
        return awakeThread;
    }
}
