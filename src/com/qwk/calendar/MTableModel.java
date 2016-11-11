package com.qwk.calendar;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class MTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final Object[][] tableDatas;

    public MTableModel(String[] columnNames, Object[][] tableDatas) {
        super();
        this.columnNames = columnNames;
        this.tableDatas = tableDatas;
    }

    public MTableModel(Object[][] tableDatas, String[] columnNames) {
        super();
        this.tableDatas = tableDatas;
        this.columnNames = columnNames;
    }

    public int getRowCount() {
        return tableDatas.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableDatas[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tableDatas[rowIndex][columnIndex] = aValue;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return JButton.class;
    }
}
