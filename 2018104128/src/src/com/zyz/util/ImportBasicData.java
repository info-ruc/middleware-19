package com.zyz.util;

import com.csvreader.CsvReader;
import com.zyz.jdbc.SqlMenu;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;

public class ImportBasicData {
//    private String path = ImportBasicData.class.getResource("/otherfiles/wuliao.csv").getPath();
    private String path = ImportBasicData.class.getResource("/otherfiles/goods_info.csv").getPath();
    private  File file = new File(path);

    public void readCsvDataToSQLite() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        CsvReader csvReader = new CsvReader(reader, ' ');
        int i = 0;
        while (csvReader.readRecord()){
            String inString = csvReader.getRawRecord();
            String[] dataSet = inString.split(" ");
            if(SqlMenu.selectRows(dataSet[1]) == 0) {
                if(dataSet.length<4){
                SqlMenu.firstInsertData(dataSet[1], dataSet[2], "无", "未知", 0.0, 0);
                }else {
                    SqlMenu.firstInsertData(dataSet[1], dataSet[2], dataSet[3], "未知", 0.0, 0);
                }
            }
            System.out.println(i++);
        }
    }
}
