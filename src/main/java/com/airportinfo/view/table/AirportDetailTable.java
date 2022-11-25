package com.airportinfo.view.table;

import com.airportinfo.Airport;
import com.airportinfo.controller.AirportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;


public  class AirportDetailTable extends AirportView {
    private final JPanel panel = new JPanel();
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] header;
    public AirportDetailTable() {
        header = new String[] {"EnglishName", "KoreanName", "IATA", "ICAO", "Region",
                "EnglishCountryName", "KoreanCountryName", "EnglishCityName"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }


    @Override
    public  void updateView() {
        try {
            AirportController airportCon = new AirportController();
            while(tableModel.getRowCount() > 0)
                tableModel.removeRow(0);
            if(airports == null)
                return;
            for(Airport airport : airportCon.getAirports()) {
                Object[] rowData = createRowData(airport);
                tableModel.addRow(rowData);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }

    }

    private Object[] createRowData(Airport airport) {
        return airport.toArray();
    }

}
