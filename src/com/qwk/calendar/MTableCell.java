package com.qwk.calendar;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MTableCell extends JPanel implements TableCellRenderer {

    private static String selectedDay;

    public MTableCell() {
        setLayout(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton) value;
        if (hasFocus && button.isEnabled()) {
            selectedDay = button.getText();
        }
        removeAll();
        add(button);
        return this;
    }

    public static String getSelectedDay() {
        return selectedDay;
    }

    public static void setSelectedDay(String selectedDay) {
        MTableCell.selectedDay = selectedDay;
    }
}
