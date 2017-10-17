/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tdvdev
 */
public class FileReader {
    public static List<Set<String>> readFile(String datasource){
        String basket = "";
                List<Set<String>> transactions = new ArrayList<Set<String>>();
                try {
                    java.io.FileReader fileReader = new java.io.FileReader(datasource);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while ((basket = bufferedReader.readLine()) != null) {
                        String items[] = basket.split(" ");
                        transactions.add(new HashSet<String>(Arrays.asList(items)));
                    }
                    // Always close files.
                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("Unable to open file '" + datasource + "'");
                } catch (IOException ex) {
                    System.out.println("Error reading file '" + datasource + "'");
                }
                return transactions;
    }
    public static String getExtension(String datasource) {
        String ext = null;
        int i = datasource.lastIndexOf('.');

        if (i > 0 &&  i < datasource.length() - 1) {
            ext = datasource.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
