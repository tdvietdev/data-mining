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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Demo {

    public static void main(String[] args) {
        AprioriFrequentItemsetGenerator<String> generator
                = new AprioriFrequentItemsetGenerator<>();

        List<Set<String>> itemsetList = new ArrayList<>();

//        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "c", "d", "e")));
//        itemsetList.add(new HashSet<>(Arrays.asList("b", "c")));
//        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "f")));
//        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "g")));
//        itemsetList.add(new HashSet<>(Arrays.asList("a", "f", "h")));

        itemsetList.add(new HashSet<>(Arrays.asList("a", "c", "d")));
        itemsetList.add(new HashSet<>(Arrays.asList("b","c","e")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "c","e")));
        itemsetList.add(new HashSet<>(Arrays.asList("b", "e")));
        FrequentItemsetData<String> data = generator.generate(itemsetList, 0.4);
        int i = 1;

        for (Set<String> itemset : data.getFrequentItemsetList()) {
            System.out.printf("%2d: %9s, support: %1.1f\n",
                    i++,
                    itemset,
                    data.getSupport(itemset));
        }
//        
//        System.out.println(data.getFrequentItemsetList());
//        System.out.println(data.getSupportCountMap());

        
        
    }
}
