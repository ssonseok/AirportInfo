package com.airportinfo.view.table;

import com.airportinfo.Airport;
import com.airportinfo.view.AirportView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * An abstract view class handling airports table.
 *
 * @author JumoKookbob
 */
public  class AirportDetailTable extends AirportView {
    private final JPanel panel = new JPanel();
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] header;

    /**
     * constructor method
     */
    public AirportDetailTable() {
        header = new String[] {"EnglishName", "KoreanName", "IATA", "ICAO", "Region",
                "EnglishCountryName", "KoreanCountryName", "EnglishCityName"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    }

    /**
     *
     * @return panel
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * update view
     */
    @Override
    public  void updateView() {
        for(Airport airport : airports) {
            Object[] rowData = createRowData(airport);
            tableModel.addRow(rowData);
        }
    }

    /**
     *
     * @param airport
     * @return rowData = airport.toArray()
     */
    private Object[] createRowData(Airport airport) {
        return airport.toArray();
    }
}
