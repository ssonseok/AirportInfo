package com.airportinfo.util.fileWriter;

import com.airportinfo.Airport;
import com.airportinfo.controller.AirportController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public abstract class FileWriter {
    protected static String path = "C:\\Users\\lkhee\\Desktop\\fileFolder";
    private File folder;
    /**
     * constructor method
     */
    public FileWriter() {
        folder = new File(path);
        if(!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        }
    }

    public void writeHeader(String[] header, BufferedWriter bWriter) throws IOException {
        int count = 0;
        for(String head : header) {
            if(count < header.length - 1)
                bWriter.write(head + ",");
            else
                bWriter.write(head);
            count++;
        }
        bWriter.newLine();
    }

    public void writeContents(String[][] contents, BufferedWriter bWriter) throws IOException {
        for(int i = 1; i < contents.length; i++) {
            for(String content : contents[i]){
                if(i < contents.length - 1)
                    bWriter.write(content + ",");
                else
                    bWriter.write(content);
            }
            bWriter.newLine();
        }
    }

    private String[] getHeader(){
        return new String[] {"EnglishName", "KoreanName", "IATA", "ICAO", "Region",
                "EnglishCountryName", "KoreanCountryName", "EnglishCityName"};
    }

    protected void downloading(BufferedWriter bWriter) throws SQLException, ClassNotFoundException, IOException {
        try {
            String[] dbHeader = getHeader();
            AirportController airportCon = new AirportController();
            Object[][] dbData = new String[airportCon.getAirports().size()][];
            for(int i = 0; i < airportCon.getAirports().size(); i++) {
                for(Airport airport : airportCon.getAirports()) {
                    dbData[i]  = (airport).toArray();
                }
            }
            writeHeader(dbHeader, bWriter);
            writeContents((String[][]) dbData, bWriter);
        } finally {
            bWriter.flush();
            bWriter.close();
        }
    }

    public abstract void downloadFile(String fName) throws IOException, SQLException, ClassNotFoundException;

}
