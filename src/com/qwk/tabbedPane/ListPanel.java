/*
 * PlanPanel.java
 *
 * Created on 2008��7��27��, ����8:59
 */
package com.qwk.tabbedPane;


import com.qwk.dao.Dao;
import com.qwk.frame.SeeNoteDialog;
import com.qwk.frame.VindicateNoteDialog;
import com.qwk.tool.AwakeThread;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

//���ڼ�¼����
public class ListPanel extends javax.swing.JPanel {
	                 
    private JButton addButton;
    private JButton delButton;
    private JToolBar.Separator jSeparator1;
    private JToolBar.Separator jSeparator2;
    private JScrollPane scrollPane;
    private JButton searchButton;
    private JTextField searchTextField;
    private JButton seeButton;
    private JTable table;
    private JToolBar toolBar;
    private JButton updButton;
 

    private static final Dao dao = Dao.getInstance();
    private final DefaultTableModel tableModel;
    private Vector<Vector> notes;
    

    /** Creates new form PlanPanel */
    public ListPanel() {
        initComponents();//��ʼ�����漰�ؼ�
        tableModel = (DefaultTableModel) table.getModel();
    }
    
    public void setDatas(){
    	notes = dao.sNoteByType(getName());
    	initTable();
    }

    public void setNotes(Vector<Vector> notes) {
        this.notes = notes;
    }

    public void initTable() {
        String[] columnNames = {"����", "ʱ��", "����", "����", "����"};
        Object[][] tableValues = {null};
        
        int rowCount = notes.size();
        if (rowCount > 0) {
            tableValues = new Object[rowCount][5];
            for (int row = 0; row < rowCount; row++) {
                Vector note = notes.get(row);
                tableValues[row][0] = note.get(2);
                tableValues[row][1] = note.get(3);
                int awake = Integer.valueOf(note.get(5).toString());
                tableValues[row][2] = (awake == 1 ? "��" : "");
                tableValues[row][3] = note.get(4);
                tableValues[row][4] = note.get(6);
            }
        }
        tableModel.setDataVector(tableValues, columnNames);
    }
                 
    private void initComponents() {

        toolBar = new JToolBar();
        seeButton = new JButton();
        jSeparator1 = new JToolBar.Separator();
        addButton = new JButton();
        updButton = new JButton();
        delButton = new JButton();
        jSeparator2 = new JToolBar.Separator();
        searchTextField = new JTextField();
        searchButton = new JButton();
        scrollPane = new JScrollPane();
        table = new JTable();

        setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        seeButton.setText("�鿴");
        seeButton.setFocusable(false);
        seeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        seeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        seeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seeButtonActionPerformed(evt);
            }
        });
        toolBar.add(seeButton);

        addButton.setText("���");
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        toolBar.add(addButton);

        updButton.setText("�޸�");
        updButton.setFocusable(false);
        updButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        updButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        updButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updButtonActionPerformed(evt);
            }
        });
        toolBar.add(updButton);

        delButton.setText("ɾ��");
        delButton.setFocusable(false);
        delButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });
        toolBar.add(delButton);
        toolBar.add(jSeparator2);

        searchTextField.setMaximumSize(new java.awt.Dimension(150, 2147483647));
        toolBar.add(searchTextField);

        searchButton.setText("����");
        searchButton.setFocusable(false);
        searchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        toolBar.add(searchButton);


        add(toolBar, java.awt.BorderLayout.PAGE_START);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "����", "ʱ��", "����", "����", "����"
            }
        ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(table);

        add(scrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        

private void seeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
// TODO add your handling code here:
    int selectedRowCount = table.getSelectedRowCount();
    if (selectedRowCount != 1) {
        String info;
        if (selectedRowCount < 1) {
            info = "��ѡ��Ҫ�鿴��" + getName() + "!";
        } else {
            info = "ÿ��ֻ�ܲ鿴һ��" + getName() + "!";
        }
        JOptionPane.showMessageDialog(this, info, "������ʾ", JOptionPane.INFORMATION_MESSAGE);
    } else {
        SeeNoteDialog dialog = new SeeNoteDialog(null, true);
        dialog.setSeeNote(notes.get(table.getSelectedRow()));
        dialog.setVisible(true);
    }
}                                         

private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
	// TODO add your handling code here:
    String type = getName();
    VindicateNoteDialog dialog = new VindicateNoteDialog(null, true);
    dialog.setType(type);
    dialog.setVisible(true);
    notes = dao.sNoteByType(type);
    initTable();
}                                         

private void updButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
// TODO add your handling code here:
    int selectedRowCount = table.getSelectedRowCount();
    if (selectedRowCount != 1) {
        String info;
        if (selectedRowCount < 1) {
            info = "��ѡ��Ҫ�޸ĵ�" + getName() + "!";
        } else {
            info = "ÿ��ֻ���޸�һ��" + getName() + "!";
        }
        JOptionPane.showMessageDialog(this, info, "������ʾ", JOptionPane.INFORMATION_MESSAGE);
    } else {
        String type = getName();
        VindicateNoteDialog dialog = new VindicateNoteDialog(null, true);
        dialog.setType(type);
        dialog.setUpdateRow(notes.get(table.getSelectedRow()));
        dialog.setVisible(true);
        notes = dao.sNoteByType(type);
        initTable();
    }
}                                         

private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
// TODO add your handling code here:    
    int selectedRowCount = table.getSelectedRowCount();
    if (selectedRowCount < 1) {
        JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ����" + getName() + "!", "������ʾ",
                JOptionPane.INFORMATION_MESSAGE);
    } else {
        int d = JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ��ѡ�е�" + getName() + "?", "������ʾ",
                JOptionPane.YES_NO_OPTION);
        if (d == 0) {
            int[] selectedRows = table.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                dao.dNote(notes.get(selectedRows[i]).get(0).toString());
            }
            String type = getName();
            notes = dao.sNoteByType(type);
            initTable();
            AwakeThread awakeThread = AwakeThread.getInstance();
            if (awakeThread.isAlive()) {
                awakeThread.refresh();
            }

        }
    }
}                                         

private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
// TODO add your handling code here:
    String type = getName();
    notes = dao.sNoteByKeywords(type, searchTextField.getText().trim());
    initTable();
}                                            

                                                  

                   
}
