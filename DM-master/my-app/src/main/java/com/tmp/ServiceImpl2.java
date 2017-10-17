package com.tmp;
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
import java.util.Map.Entry;
public class ServiceImpl2 {
	//PASS1
	public Map<List<String>, Integer>  countSupportCandidate(String data){
		
		List<String> mappings = new ArrayList<String>();;
		List<Integer> itemCounts = new ArrayList<Integer>();
		Map<List<String>, Integer> candidates = new HashMap<List<String>, Integer>();
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
	
	public Map<List<String>, Integer> pruneNonFrequent(Map<List<String>, Integer> candidates, int support){
		
		Map<List<String>, Integer> frequentCandidates = new HashMap<List<String>, Integer>();
		for (Entry<List<String>, Integer> candidate : candidates.entrySet()){
			int itemCount = candidate.getValue();
			if(itemCount > support){
				frequentCandidates.put(candidate.getKey(), candidate.getValue());
			}
		}
		return frequentCandidates;
	}
	
	private List<String> getSortedItemSet(Map<List<String>, Integer> frequentCandidates){
		
		List<String> keyItems = new ArrayList<String>();
		for (List<String> key : frequentCandidates.keySet()) {
		    for (String item: key){
		    	keyItems.add(item);
			}
		}
		Collections.sort(keyItems);
		
		return keyItems;
	}
	// go to pass 2 
	
	public Map<List<String>, Integer> generateCandidatePairs(Map<List<String>, Integer> frequentCandidates) {
		
		Combination2 c = new Combination2();
		Map<List<String>, Integer> candidatePairs = new HashMap<List<String>, Integer>();
		List<String> frequentItems = getSortedItemSet(frequentCandidates);
		
		Set<List<String>> pairs = c.combination(frequentItems, 2);

		Iterator<List<String>> i = pairs.iterator();
		while (i.hasNext()) {
			candidatePairs.put((ArrayList<String>) i.next(), new Integer(0));
		}

		return candidatePairs;
	}
	
	public Map<List<String>, Integer> countCandidatePairs(Map<List<String>, Integer> candidatePairs, String data){
		
		String basket = "";
		Set<List<String>> pairs;
		Combination2 c = new Combination2();
		try {
			
            FileReader fileReader = new FileReader(data);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(( basket = bufferedReader.readLine()) != null) {
            	List<String> fItems = new ArrayList<String>();
            	
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		fItems.add(item);
            	}
        		// Generate pairs for them and update counts
            	Collections.sort(fItems);
        		pairs = c.combination(fItems,2);
            	
        		Iterator<List<String>> i = pairs.iterator();
        		while (i.hasNext()) {
        			List<String> pair = i.next();
        			if(candidatePairs.containsKey(pair)){
        				int count = candidatePairs.get(pair);
        	            candidatePairs.put(pair, count+1);
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
		
		return candidatePairs;
	}
	
	public Map<List<String>, Integer> pruneNonFrequentPairs(Map<List<String>, Integer> candidatePairs, int support){
		
		Map<List<String>, Integer> frequentCandidates = new HashMap<List<String>, Integer>();
		for (Entry<List<String>, Integer> candidate : candidatePairs.entrySet()){
			int itemCount = candidate.getValue();
			if(itemCount > support){
				frequentCandidates.put(candidate.getKey(), candidate.getValue());
			}
		}
		return frequentCandidates;
	}
	
	
}
