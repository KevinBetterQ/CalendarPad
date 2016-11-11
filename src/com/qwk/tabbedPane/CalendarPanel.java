/*
 * CalendarPanel.java
 *
 * Created on 2008年7月27日, 上午8:44
 */
package com.qwk.tabbedPane;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.qwk.calendar.MButton;
import com.qwk.calendar.MTableCell;
import com.qwk.calendar.MTableModel;


//一个初始化日历界面的类
public class CalendarPanel extends javax.swing.JPanel {
	
	// Variables declaration - do not modify                     
    private javax.swing.JButton addMonthButton;
    private javax.swing.JButton addYearButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JTextField monthTextField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton subMonthButton;
    private javax.swing.JButton subYearButton;
    private javax.swing.JTable table;
    private javax.swing.JButton todayButton;
    private javax.swing.JLabel todayLabel;
    private javax.swing.JPanel todayPanel;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration       

    private static final int YEAR;
    private static final int MONTH;
    private static final int DAY;
    private static int year;
    private static int month;
    private static final int[] daysOfMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private MTableModel tableModel;
    private static final DateFormat dateFormat = DateFormat.getDateInstance();
    

    static {
        Calendar today = Calendar.getInstance();
        YEAR = year = today.get(Calendar.YEAR);
        MONTH = month = today.get(Calendar.MONTH) + 1;
        DAY = today.get(Calendar.DAY_OF_MONTH);
        judgeLeapYear();
    }

    /** Creates new form CalendarPanel */
    public CalendarPanel() {
        initComponents();//初始化各个组件
        table.setDefaultRenderer(JButton.class, new MTableCell());
        tableModel = (MTableModel) table.getModel();
        yearTextField.setText(YEAR+"");
        monthTextField.setText(MONTH+"");
        todayLabel.setText("今天：" + YEAR + " 年 " + MONTH + " 月 " + DAY + " 日");
        initTableModel();//负责初始化日历表格的模型
    }

    //负责初始化日历表格的模型
    private void initTableModel() {
        int row = 0;
        int col = 0;
        MButton button = null;
        String color = "CCCCCC";
        String text = "<html><div align='center' style='color:#";
        String text2 = "'><font size='+2'>";
        String text3 = "</font><br><font size='-1'>";
        String text4 = "</font></div></html>";
        //
        try {
            dateFormat.parse(year + "-" + month + "-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar firstDayOfMonth = dateFormat.getCalendar();
        int dayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        int lastMonthDays = 31;
        if (month > 2) {
            lastMonthDays = daysOfMonth[month - 1];
        }
        int y = year;
        int m = month;
        if (m == 1) {
            m = 12;
            y--;
        } else {
            m--;
        }
        for (int day = lastMonthDays - dayOfWeek + 2; day <= lastMonthDays; day++) {
            button = new MButton(text + color + text2 + day + text3 +  text4);
            button.setEnabled(false);
            tableModel.setValueAt(button, row, col);
            if (col == 6) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
        //
        for (int day = 1; day <= daysOfMonth[month]; day++) {
            if (year == YEAR && month == MONTH && day == DAY) {
                color = "FF00FF";
            } else {
                if (col > 4) {
                    if (col == 5) {
                        color = "00FF00";
                    } else {
                        color = "FF0000";
                    }
                } else {
                    color = "000000";
                }
            }
            button = new MButton(text + color + text2 + day + text3 +  text4);
            tableModel.setValueAt(button, row, col);
            if (col == 6) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
        //
        color = "CCCCCC";
        int nextMonthDays = 42 - (row * 7 + col);
        y = year;
        m = month;
        if (m == 12) {
            m = 1;
            y++;
        } else {
            m++;
        }
        for (int day = 1; day <= nextMonthDays; day++) {
            button = new MButton(text + color + text2 + day + text3 +  text4);
            button.setEnabled(false);
            tableModel.setValueAt(button, row, col);
            if (col == 6) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
    }

    public static void judgeLeapYear() {
        if (year % 100 == 0) {
            daysOfMonth[2] = (year % 400 == 0 ? 29 : 28);
        } else {
            daysOfMonth[2] = (year % 4 == 0 ? 29 : 28);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //初始化各个组件               
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        subYearButton = new javax.swing.JButton();
        subMonthButton = new javax.swing.JButton();
        yearTextField = new javax.swing.JTextField();
        yearLabel = new javax.swing.JLabel();
        monthTextField = new javax.swing.JTextField();
        monthLabel = new javax.swing.JLabel();
        addMonthButton = new javax.swing.JButton();
        addYearButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        todayPanel = new javax.swing.JPanel();
        todayLabel = new javax.swing.JLabel();
        todayButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        subYearButton.setText("<<");
        subYearButton.setToolTipText("上一年");//设置鼠标停留信息
        subYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subYearButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(subYearButton);

        subMonthButton.setText("<");
        subMonthButton.setToolTipText("上一月");
        subMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMonthButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(subMonthButton);

        yearTextField.setColumns(6);
        yearTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        yearTextField.setText("2016");
        yearTextField.setName("year"); 
        yearTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFieldFocusLost(evt);
            }
        });
        yearTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                yearTextFieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yearTextFieldKeyTyped(evt);
            }
        });
        buttonPanel.add(yearTextField);

        yearLabel.setText("年");
        buttonPanel.add(yearLabel);

        monthTextField.setColumns(4);
        monthTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        monthTextField.setText("08");
        monthTextField.setName("month"); // NOI18N
        monthTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFieldFocusLost(evt);
            }
        });
        monthTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monthTextFieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                monthTextFieldKeyTyped(evt);
            }
        });
        buttonPanel.add(monthTextField);

        monthLabel.setText("月");
        buttonPanel.add(monthLabel);

        addMonthButton.setText(">");
        addMonthButton.setToolTipText("下一月");
        addMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMonthButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(addMonthButton);

        addYearButton.setText(">>");
        addYearButton.setToolTipText("下一年");
        addYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addYearButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(addYearButton);

        add(buttonPanel, java.awt.BorderLayout.PAGE_START);

        table.setModel(new com.qwk.calendar.MTableModel(
            new Object[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String[]{
                "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
            }));
            table.setRowHeight(60);
            table.setRowSelectionAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.getTableHeader().setReorderingAllowed(false);
            scrollPane.setViewportView(table);

            add(scrollPane, java.awt.BorderLayout.CENTER);

            todayLabel.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
            todayLabel.setForeground(new java.awt.Color(255, 0, 255));
            todayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            todayLabel.setText("今天：2016 年 8 月 8 日");
            todayPanel.add(todayLabel);

            todayButton.setText("回到今天");
            todayButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    todayButtonActionPerformed(evt);
                }
            });
            todayPanel.add(todayButton);

            add(todayPanel, java.awt.BorderLayout.SOUTH);
        }// </editor-fold>                        

private void subYearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
// TODO add your handling code here:
    yearTextField.setText(--year + "");
    judgeLeapYear();
    initTableModel();
    SwingUtilities.updateComponentTreeUI(table);
}                                             

private void addYearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
// TODO add your handling code here:
    yearTextField.setText(++year + "");
    judgeLeapYear();
    initTableModel();
    SwingUtilities.updateComponentTreeUI(table);
}                                             

private void subMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
// TODO add your handling code here:
    if (month == 1) {
        yearTextField.setText(--year + "");
        judgeLeapYear();
        month = 12;
    } else {
        month--;
    }
    monthTextField.setText(month + "");
    initTableModel();
    SwingUtilities.updateComponentTreeUI(table);
}                                              

private void addMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
// TODO add your handling code here:
    if (month == 12) {
        yearTextField.setText(++year + "");
        judgeLeapYear();
        month = 1;
    } else {
        month++;
    }
    monthTextField.setText(month + "");
    initTableModel();
    SwingUtilities.updateComponentTreeUI(table);
}                                              

private void textFieldFocusGained(java.awt.event.FocusEvent evt) {                                      
// TODO add your handling code here:
    JTextField textField = (JTextField) evt.getSource();
    textField.setText(null);
}                                     

private void textFieldFocusLost(java.awt.event.FocusEvent evt) {                                    
// TODO add your handling code here:
    JTextField textField = (JTextField) evt.getSource();
    if (textField.getText().length() == 0) {
        if (textField.getName().equals("year")) {
            textField.setText(year + "");
        } else {//month
            textField.setText(month + "");
        }
    }
}                                   

private void yearTextFieldKeyTyped(java.awt.event.KeyEvent evt) {                                       
// TODO add your handling code here:
    int digit = yearTextField.getText().length();
    if (digit < 4) {
        String num = (digit == 0 ? "123456789" : "0123456789");
        if (num.indexOf(evt.getKeyChar()) < 0) {
            evt.consume();
        }
    } else {
        evt.consume();
    }
}                                      

private void yearTextFieldKeyReleased(java.awt.event.KeyEvent evt) {                                          
// TODO add your handling code here:
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        year = Integer.valueOf(yearTextField.getText());
        judgeLeapYear();
        initTableModel();
        SwingUtilities.updateComponentTreeUI(table);
    }
}                                         

private void monthTextFieldKeyTyped(java.awt.event.KeyEvent evt) {                                        
// TODO add your handling code here:
    String input = monthTextField.getText();
    switch (input.length()) {
        case 0:
            if ("123456789".indexOf(evt.getKeyChar()) < 0) {
                evt.consume();
            }
            break;
        case 1:
            if (input.equals("1")) {
                if ("012".indexOf(evt.getKeyChar()) < 0) {
                    evt.consume();
                }
            } else {
                evt.consume();
            }
            break;
        default:
            evt.consume();
    }
}                                       

private void monthTextFieldKeyReleased(java.awt.event.KeyEvent evt) {                                           
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        month = Integer.valueOf(monthTextField.getText());
        initTableModel();
        SwingUtilities.updateComponentTreeUI(table);
    }
}                                          

private void todayButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
// TODO add your handling code here:
    year = YEAR;
    month = MONTH;
    yearTextField.setText(year + "");
    monthTextField.setText(month + "");
    judgeLeapYear();
    initTableModel();
    SwingUtilities.updateComponentTreeUI(table);
}                                           

                
}
