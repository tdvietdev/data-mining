/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mining;

/**
 *
 * @author tdvdev
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ApachePOIExcelRead {

    private static final String FILE_NAME = "/home/tdvdev/Desktop/learn_laravel/public/document/excel/test01.xlsx";

    public static void main(String[] args) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            ArrayList<String> firstItem = new ArrayList<>();
            List<Set<String>> itemsetList = new ArrayList<>();

            // get first
            if (iterator.hasNext()) {

                Row firstRow = iterator.next();
                Iterator<Cell> cellIterator = firstRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
//                        System.out.print(currentCell.getStringCellValue() + "--");
                        firstItem.add(currentCell.getStringCellValue());
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
//                        System.out.print(currentCell.getNumericCellValue() + "--");
                        firstItem.add(String.valueOf(currentCell.getNumericCellValue()));
                    }
                }
            }
//            System.out.println(firstItem);

            while (iterator.hasNext()) {
                int mCount = 0;
                HashSet<String> mList = new HashSet<>();
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();

                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        mList.add(firstItem.get(mCount) + "-" + currentCell.getStringCellValue()) ;
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        mList.add(firstItem.get(mCount) + "-" + currentCell.getNumericCellValue()) ;
                    }
                    mCount ++;
                    

                }
                itemsetList.add(mList);
            }
//            return itemsetList;
System.out.println(itemsetList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
