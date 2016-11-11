package com.qwk.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

public class CalendarDialog extends JDialog {

    public static void main(String args[]) {
        try {
            CalendarDialog dialog = new CalendarDialog();
            dialog.addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final int YEAR;
    private static final int MONTH;
    private static final int DAY;
    private static int year;
    private static int month;
    private static final int[] daysOfMonth = {0, 31, 28, 31, 30, 31, 30, 31,
        31, 30, 31, 30, 31
    };
    private JTextField yearTextField;
    private JTextField monthTextField;
    private MTableModel tableModel;
    private JTable table;
    

    static {
        Calendar today = Calendar.getInstance();
        YEAR = year = today.get(Calendar.YEAR);
        MONTH = month = today.get(Calendar.MONTH) + 1;
        DAY = today.get(Calendar.DAY_OF_MONTH);
        judgeLeapYear();
    }

    public CalendarDialog() {
        super();
        setModal(true);
        setTitle("万年历");
        setResizable(false);

        // 无标题栏
        setUndecorated(true);
        setBounds(100, 100, 268, 219);

        // 有标题栏
//		setBounds(100, 100, 274, 251);

        final JPanel borderPanel = new JPanel();
        borderPanel.setBorder(new TitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        borderPanel.setLayout(new BorderLayout());
        getContentPane().add(borderPanel, BorderLayout.CENTER);

        final JPanel operatePanel = new JPanel();
        borderPanel.add(operatePanel, BorderLayout.NORTH);

        final JButton subYearButton = new JButton();
        subYearButton.setText("<<");
        subYearButton.setMargin(new Insets(0, 5, 0, 5));
        subYearButton.addActionListener(new SubYearButtonAL());
        operatePanel.add(subYearButton);

        final JButton subMonthButton = new JButton();
        subMonthButton.setText("<");
        subMonthButton.setMargin(new Insets(0, 8, 0, 8));
        subMonthButton.addActionListener(new SubMonthButtonAL());
        operatePanel.add(subMonthButton);

        yearTextField = new JTextField();
        yearTextField.setColumns(4);
        yearTextField.setText(year + "");
        yearTextField.setFont(new Font("", Font.BOLD, 12));
        yearTextField.setHorizontalAlignment(SwingConstants.CENTER);
        yearTextField.addFocusListener(new YearTextFieldFL());
        yearTextField.addKeyListener(new YearTextFieldKL());
        operatePanel.add(yearTextField);

        final JLabel yearLabel = new JLabel();
        yearLabel.setText("年");
        operatePanel.add(yearLabel);

        monthTextField = new JTextField();
        monthTextField.setColumns(2);
        monthTextField.setText(month + "");
        monthTextField.setFont(new Font("", Font.BOLD, 12));
        monthTextField.setHorizontalAlignment(SwingConstants.CENTER);
        monthTextField.addFocusListener(new MonthTextFieldFL());
        monthTextField.addKeyListener(new MonthTextFieldKL());
        operatePanel.add(monthTextField);

        final JLabel monthLabel = new JLabel();
        monthLabel.setText("月");
        operatePanel.add(monthLabel);

        final JButton addMonthButton = new JButton();
        addMonthButton.setText(">");
        addMonthButton.setMargin(new Insets(0, 8, 0, 8));
        addMonthButton.addActionListener(new AddMonthButtonAL());
        operatePanel.add(addMonthButton);

        final JButton addYearButton = new JButton();
        addYearButton.setText(">>");
        addYearButton.setMargin(new Insets(0, 5, 0, 5));
        addYearButton.addActionListener(new AddYearButtonAL());
        operatePanel.add(addYearButton);

        final JScrollPane scrollPane = new JScrollPane();
        borderPanel.add(scrollPane, BorderLayout.CENTER);

        String[] columnNames = {"一", "二", "三", "四", "五", "六", "日"};
        Object[][] tableDatas = new Object[6][7];

        tableModel = new MTableModel(columnNames, tableDatas);

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setRowSelectionAllowed(false);
        table.setDefaultRenderer(JButton.class, new MTableCell());
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("", Font.BOLD, 12));
        scrollPane.setViewportView(table);

        final JPanel todayPanel = new JPanel();
        borderPanel.add(todayPanel, BorderLayout.SOUTH);

        final JLabel todayLabel = new JLabel();
        todayLabel.setText("今天是：" + YEAR + "-" + MONTH + "-" + DAY + "  ");
        todayPanel.add(todayLabel);
        final JButton todayButton = new JButton();
        todayButton.setText(". . .");
        todayButton.setMargin(new Insets(0, 3, 0, 3));
        todayButton.addActionListener(new TodayButtonAL());
        todayPanel.add(todayButton);
        //
        initTableModel();
    }

    private void initTableModel() {
        int row = 0;
        int col = 0;
        MButton button = null;
        // 上一月
        DateFormat dateFormat = DateFormat.getDateInstance();
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
        for (int day = lastMonthDays - dayOfWeek + 2; day <= lastMonthDays; day++) {
            button = new MButton(day);
            button.setEnabled(false);
            tableModel.setValueAt(button, row, col);
            if (col == 6) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
        // 当月
        for (int day = 1; day <= daysOfMonth[month]; day++) {
            button = new MButton(day);
            if (col > 4) {
                if (col == 5) {
                    button.setForeground(Color.GREEN);
                } else {
                    button.setForeground(Color.RED);
                }
            }
            if (day == DAY) {
                if (year == YEAR && month == MONTH) {
                    button.setForeground(Color.ORANGE);
                }
            }
            tableModel.setValueAt(button, row, col);
            if (col == 6) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
        // 下一月
        int nextMonthDays = 42 - (row * 7 + col);
        for (int day = 1; day <= nextMonthDays; day++) {
            button = new MButton(day);
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

    private class SubYearButtonAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            yearTextField.setText(--year + "");
            judgeLeapYear();
            initTableModel();
            SwingUtilities.updateComponentTreeUI(table);
        }
    }

    private class AddYearButtonAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            yearTextField.setText(++year + "");
            judgeLeapYear();
            initTableModel();
            SwingUtilities.updateComponentTreeUI(table);
        }
    }

    private class SubMonthButtonAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
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
    }

    private class AddMonthButtonAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
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
    }

    private class YearTextFieldFL implements FocusListener {

        public void focusGained(FocusEvent e) {
            yearTextField.setText(null);
        }

        public void focusLost(FocusEvent e) {
            if (yearTextField.getText().length() == 0) {
                yearTextField.setText(year + "");
            }
        }
    }

    private class YearTextFieldKL extends KeyAdapter {

        public void keyTyped(KeyEvent e) {
            int digit = yearTextField.getText().length();
            if (digit < 4) {
                String num = (digit == 0 ? "123456789" : "0123456789");
                if (num.indexOf(e.getKeyChar()) < 0) {
                    e.consume();
                }
            } else {
                e.consume();
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                year = Integer.valueOf(yearTextField.getText());
                judgeLeapYear();
                initTableModel();
                SwingUtilities.updateComponentTreeUI(table);
            }
        }
    }

    private class MonthTextFieldFL implements FocusListener {

        public void focusGained(FocusEvent e) {
            monthTextField.setText(null);
        }

        public void focusLost(FocusEvent e) {
            if (monthTextField.getText().length() == 0) {
                monthTextField.setText(month + "");
            }
        }
    }

    private class MonthTextFieldKL extends KeyAdapter {

        public void keyTyped(KeyEvent e) {
            String input = monthTextField.getText();
            switch (input.length()) {
                case 0:
                    if ("123456789".indexOf(e.getKeyChar()) < 0) {
                        e.consume();
                    }
                    break;
                case 1:
                    if (input.equals("1")) {
                        if ("012".indexOf(e.getKeyChar()) < 0) {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                    break;
                default:
                    e.consume();
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                month = Integer.valueOf(monthTextField.getText());
                initTableModel();
                SwingUtilities.updateComponentTreeUI(table);
            }
        }
    }

    private class TodayButtonAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            year = YEAR;
            month = MONTH;
            yearTextField.setText(year + "");
            monthTextField.setText(month + "");
            judgeLeapYear();
            initTableModel();
            SwingUtilities.updateComponentTreeUI(table);
        }
    }

    public static void judgeLeapYear() {
        if (year % 100 == 0) {
            daysOfMonth[2] = (year % 400 == 0 ? 29 : 28);
        } else {
            daysOfMonth[2] = (year % 4 == 0 ? 29 : 28);
        }
    }

    public static int getYear() {
        return year;
    }

    public static int getMonth() {
        return month;
    }

    public static int getYEAR() {
        return YEAR;
    }

    public static int getMONTH() {
        return MONTH;
    }

    public static int getDAY() {
        return DAY;
    }
}
