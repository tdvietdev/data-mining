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

import com.mycompany.app.Combination;

public class ServicesImpl {
	
	//============================ PASS 1 ===============================
	
	 
	public ItemSets generateCandidate(String data){

		ItemSets itemSets = new ItemSets();
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
    	
    	itemSets.setTransaction(transaction);
    	itemSets.setMappings(mappings);
    	itemSets.setItemCounts(itemCounts);
		
		return itemSets;
	}
	
	public List<Integer> pruneNonFrequent(ItemSets itemSets, int support){
		
		List<Integer> frequentItems = new ArrayList<Integer>();
		List<String> mappings = itemSets.getMappings();
		List<Integer> itemCounts = itemSets.getItemCounts();
		
		for (String item : mappings){
    		int counter = itemCounts.get(mappings.indexOf(item));
    		if(counter > support){
    			frequentItems.add(mappings.indexOf(item));
    		}
    	}
		return frequentItems;
	}
	// ====================== PASS 2 ===================================
	//Get all candidate pairs
	public Map<List<Integer>, Integer> generateCandidatePairs(List<Integer> frequentItems){
		
		Map<List<Integer>, Integer> candidatePairs = new HashMap<List<Integer>, Integer>();
		Combination c = new Combination();
    	Collections.sort(frequentItems);
		Set<List<Integer>> pairs = c.combination(frequentItems,2);
    	
		Iterator<List<Integer>> i = pairs.iterator();
		while (i.hasNext()) {
			candidatePairs.put((ArrayList<Integer>) i.next(), new Integer(0));
		}
		
		return candidatePairs;
	}
	
	// Get counts for all candidate pairs.
	public Map<List<Integer>, Integer> countCandidatePairs(ItemSets countItem, Map<List<Integer>, Integer> candidatePairs, String data){
		
		List<String> mappings = countItem.getMappings();
		String basket = "";
		Set<List<Integer>> pairs;
		Combination c = new Combination();
		try {
			
            FileReader fileReader = new FileReader(data);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(( basket = bufferedReader.readLine()) != null) {
            	List<Integer> fItems = new ArrayList<Integer>();
            	
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		fItems.add(mappings.indexOf(item));
            	}
        		// Generate pairs for them and update counts
            	Collections.sort(fItems);
        		pairs = c.combination(fItems,2);
            	
        		Iterator<List<Integer>> i = pairs.iterator();
        		while (i.hasNext()) {
        			List<Integer> pair = i.next();
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
	
	public Set<List<Integer>> pruneNonFrequentPairs(Map<List<Integer>, Integer> candidatePairs, int support){
		Set<List<Integer>> frequentPairs = new HashSet<List<Integer>>();
		Set<Entry<List<Integer>, Integer>> set = candidatePairs.entrySet();

		Iterator<Entry<List<Integer>, Integer>> iterator = set.iterator();
	      // Display elements
	      while(iterator.hasNext()) {
	    	 Map.Entry<List<Integer>, Integer> me = (Map.Entry<List<Integer>, Integer>) iterator.next();
	         int value = (Integer)me.getValue();
	         if(value > support){
	        	 List<Integer> key = (List<Integer>) me.getKey();
	        	 frequentPairs.add(key);
	         }
	      }
	      
		return frequentPairs;
	}
	//======================= PASS 3 ==============================
	//Generate candidate triples by frequentPairs JOIN frequentPairs
	public Set<List<Integer>> generateCandidateTriple(Set<List<Integer>> frequentPairs){
		Set<List<Integer>> allCandidateTriples  = new HashSet<List<Integer>>();
		
		for (List<Integer> fcPair: frequentPairs){
			for(List<Integer> joinPair : frequentPairs){
				if(!fcPair.containsAll(joinPair)){
					if(fcPair.get(1).equals(joinPair.get(0))){
						System.out.println("->"+Arrays.toString(fcPair.toArray()) +" " +Arrays.toString(joinPair.toArray()));
						
						List<Integer> triple = new ArrayList<Integer>(
	                            Arrays.asList(fcPair.get(0),fcPair.get(1),joinPair.get(1)));
						allCandidateTriples.add(triple);
					}
				}
			}
		}
		
		return allCandidateTriples;
	}
	
	// Prune non frequent candidate triples 
	public Map<List<Integer>, Integer> pruneNonFrequentTriples(Set<List<Integer>> allCandidateTriples,Set<List<Integer>> frequentPairs ){
		
		Map<List<Integer>, Integer> candidateTriples = new HashMap<List<Integer>, Integer>();
		Set<List<Integer>> pairs;
		Combination c = new Combination();
		for (List<Integer> candiate : allCandidateTriples){
			pairs = c.combination(candiate, 2);
			boolean whatAboutIt = true;
			for(List<Integer> pair: pairs){
				// If this pair is not in frequent pairs then 
				// this candidate eliminated from candidate triples
				if(!frequentPairs.contains(pair)){
					whatAboutIt = false;
					break;
				}
			}
			if(whatAboutIt){
				candidateTriples.put(candiate, new Integer(0));
			}
		}
		return candidateTriples;
	}
	// Get count for candidate triples 
	public Map<List<Integer>, Integer> countCandidateTriples(ItemSets countItem, Map<List<Integer>, Integer> candidateTriples, String data){
		
		List<String> mappings = countItem.getMappings();
		String basket = "";
		Combination c = new Combination();
		try {
            FileReader fileReader = new FileReader(data);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(( basket = bufferedReader.readLine()) != null) {
            	List<Integer> fItems = new ArrayList<Integer>();
            	
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		fItems.add(mappings.indexOf(item));
            	}
        		
        		// Generate pairs for them and update counts
            	Collections.sort(fItems);
        		Set<List<Integer>> triples = c.combination(fItems,3);
            	
        		Iterator<List<Integer>> iterTriples = triples.iterator();
        		while (iterTriples.hasNext()) {
        			List<Integer> triple = iterTriples.next();
        			if(candidateTriples.containsKey(triple)){
        				int count = candidateTriples.get(triple);
        				candidateTriples.put(triple, count+1);
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
		return candidateTriples;
	}
	
	//// Get frequent triples
	public Map<List<Integer>, Integer> getFrequentTriples(Map<List<Integer>, Integer> candidateTriples, int support){
		Set<List<Integer>> frequentTriples = new HashSet<List<Integer>>();
		
		for (Map.Entry<List<Integer>, Integer> candidate : candidateTriples.entrySet()){
	    	  int value = candidate.getValue();
	    	  if(value > support){
	    		  if (value > support) {
	  				List<Integer> key = (List<Integer>) candidate.getKey();
	  				frequentTriples.add(key);
	  			}
	    	  }
	    }
		
		return candidateTriples;
	}
	
}
