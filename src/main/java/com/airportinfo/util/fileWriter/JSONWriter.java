package com.airportinfo.util.fileWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class JSONWriter extends FileWriter{
    /**
     * constructor method
     *
     * @param fPath
     */
    public JSONWriter() {

    }

    @Override
    public void downloadFile(String fName) throws IOException, SQLException, ClassNotFoundException {
        File dataFile = new File(path + "/" + fName + ".json");
        dataFile.createNewFile();
        BufferedWriter fileWriter = new BufferedWriter(new java.io.FileWriter(dataFile, true));
        downloading(fileWriter);
    }
}
