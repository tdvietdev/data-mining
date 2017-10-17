package com.abc.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tmp.Combination2;

public class ServiceImpl<I> {
	
	public Map<List<String>, Integer> countCandidate(String data){
		
		Map<List<String>, Integer>  candidates = new HashMap<List<String>, Integer>();
		List<String> mappings = new ArrayList<String>();;
		List<Integer> itemCounts = new ArrayList<Integer>();
		String basket = "";
		int transaction = 0;
    	try {
            FileReader fileReader = new FileReader(data);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while(( basket = bufferedReader.readLine()) != null) {
            	transaction = transaction + 1;
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		if(!mappings.contains(item)){
            			mappings.add(item);
            			itemCounts.add(1);
            		}
            		else{
            			int indexItem = mappings.indexOf(item);
            			int counter = itemCounts.get(indexItem);
            			counter = counter + 1;
            			itemCounts.set(indexItem, counter);
            		}
            	}
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + data + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + data + "'");                  
        }
    	
    	for(int index = 0; index < mappings.size(); index ++){
    		candidates.put(Arrays.asList(mappings.get(index)), itemCounts.get(index));
    	}
    	
		return candidates;
	}
	
	//Eliminate candidates that are infrequent, leaving only those that are frequent
	public Map<List<String>, Integer> eliminateNonFrequentCandidate(Map<List<String>, Integer> candidates, int minSupport){
		Map<List<String>, Integer> frequentCandidates = new HashMap<List<String>, Integer>();
		
		for (Map.Entry<List<String>, Integer> candidate : candidates.entrySet()){
			if(candidate.getValue() >= minSupport){
				frequentCandidates.put(candidate.getKey(), candidate.getValue());
			}
		}
		
		return frequentCandidates;
	}
	
	public List<String> getSortedFrequentCandidates(Map<List<String>, Integer> frequentCandidates){
		
		List<String> frequentItems = new ArrayList<String>();
		for(List<String> items: frequentCandidates.keySet()){
			frequentItems.addAll(items);
		}
    	Collections.sort(frequentItems);
		
		return frequentItems;
	}
	public Map<List<String>, Integer> generateCandidate(Map<List<String>, Integer> frequentCandidates, int k){
		
		Map<List<String>, Integer> candidatePairs = new HashMap<List<String>, Integer>();
		Combination2 c = new Combination2();
		List<String> frequentItems = getSortedFrequentCandidates(frequentCandidates);
		Set<List<String>> pairs = c.combination(frequentItems,k);
    	
		Iterator<List<String>> i = pairs.iterator();
		while (i.hasNext()) {
			candidatePairs.put((ArrayList<String>) i.next(), new Integer(0));
		}
		
		return candidatePairs;
	}
	
	public Map<List<String>, Integer> countCandidates2(Map<List<String>, Integer> candidates, String data, int k){
	try {
			String basket = "";
            FileReader fileReader = new FileReader(data);
            Combination2 c = new Combination2();
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(( basket = bufferedReader.readLine()) != null) {
            	List<String> transactionItems = new ArrayList<String>();
            	
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		transactionItems.add(item);
            	}
        		// Generate  for them and update counts
            	Collections.sort(transactionItems);
        		Set<List<String>> kItemSets = c.combination(transactionItems,k);
            	
        		Iterator<List<String>> i = kItemSets.iterator();
        		while (i.hasNext()) {
        			List<String> kItem = i.next();
        			if(candidates.containsKey(kItem)){
        				int count = candidates.get(kItem);
        				candidates.put(kItem, count+1);
        			}
        		}
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + data + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + data + "'");                  
            
        }
		
		return candidates;
	}
	
	public Map<List<String>, Integer> generateCandidate3(Map<List<String>, Integer> candidates, int k){
		
		Map<List<String>, Integer> allCandidates = new HashMap<List<String>, Integer>();
	
		for (List<String> fcItems: candidates.keySet()){
			for(List<String> joinItems : candidates.keySet()){
				if(!fcItems.containsAll(joinItems)){
					if(fcItems.get(1).equals(joinItems.get(0))){
						System.out.println("->"+Arrays.toString(fcItems.toArray()) +" " +Arrays.toString(joinItems.toArray()));
						
						List<String> triple = new ArrayList<String>(
	                            Arrays.asList(fcItems.get(0),fcItems.get(1),joinItems.get(1)));
						allCandidates.put(triple, new Integer(0));
					}
				}
			}
		}

		return allCandidates;
	}
	
}
