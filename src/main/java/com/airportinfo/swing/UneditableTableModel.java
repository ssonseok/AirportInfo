package com.airportinfo.swing;

import javax.swing.table.DefaultTableModel;

/**
 * DefaultTableModel block editing cells.
 *
 * @author lalaalal
 */
public class UneditableTableModel extends DefaultTableModel {
    public UneditableTableModel() {
        
    }

    public UneditableTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public UneditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public UneditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
