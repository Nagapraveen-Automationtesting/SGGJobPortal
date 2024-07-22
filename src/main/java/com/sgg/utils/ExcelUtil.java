package com.sgg.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtil {

    public static void storeTableValuesIntoExcel(String path, List<LinkedHashMap<String, String>> table) {
        FileOutputStream fo = null;
        Workbook wb = null;
//		path = System.getProperty("user.dir") + "\\testData\\" + path;
        File file = new File(path);
        try {
            // D:\Workspace\Workspace_Java\Workspace_Lab\com.twitter.hybrid\testData\TestData.xlsx
//			FileInputStream fi = new FileInputStream(file);
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Result");
            Row row = sheet.createRow(0);
            List<String> headers = new LinkedList<>(table.get(0).keySet());

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            for (int i = 0; i < table.size(); i++) {
                HashMap<String, String> map = table.get(i);
                Row erow = sheet.createRow(i + 1);
                for (int j = 0; j < map.size(); j++) {
                    if (map.containsKey(headers.get(j))) {
                        erow.createCell(j).setCellValue(map.get(headers.get(j)));
                    }
                }
            }

            fo = new FileOutputStream(file);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                wb.write(fo);
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
