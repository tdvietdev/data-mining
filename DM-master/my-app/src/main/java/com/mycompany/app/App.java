package com.mycompany.app;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
/**
 * Hello world!
 *
 */
public class App 
{
	private static Map<List<Integer>, Integer> candidateTriples;
	private static List<Integer> itemCounts;
	private static Map<List<Integer>, Integer> candidatePairs;
	
    public static void main( String[] args )
    {
    	int support = 1;
    	int transaction = 0;
    	List<String> mappings = new ArrayList<String>();
    	itemCounts = new ArrayList<Integer>();
    	
    	String data = "C:/Users/khiem/Desktop/baskets1.txt";
    	String basket = "";
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
    	
    	List<Integer> frequentItems = new ArrayList<Integer>();
    	
    	for (String item : mappings){
    		//System.out.println(item);
    		int counter = itemCounts.get(mappings.indexOf(item));
    		if(counter > support){
    			frequentItems.add(mappings.indexOf(item));
    		}
    	}
    	System.out.println("frequentItems:"+frequentItems);
    	
    	
    	
    	// PASS 2
        candidatePairs = new HashMap<List<Integer>, Integer>();
		Combination c = new Combination();
    	Collections.sort(frequentItems);
		Set<List<Integer>> pairs = c.combination(frequentItems,2);
    	
		Iterator<List<Integer>> i = pairs.iterator();
		while (i.hasNext()) {
			candidatePairs.put((ArrayList<Integer>) i.next(), new Integer(0));
		}
    	
		System.out.println("CandidatePairs Pass");
		print(candidatePairs);
		
		
		// Get count for all candidate pairs
		try {
            FileReader fileReader = new FileReader(data);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(( basket = bufferedReader.readLine()) != null) {
            	List<Integer> fItems = new ArrayList<Integer>();
            	
            	String items[] = basket.split(" ");
            	for (String item:items ){
            		fItems.add(mappings.indexOf(item));
            	}
            	//Collections.sort(fItems);
        		//System.out.println("fItems:"+fItems);
        		
        		// Generate pairs for them and update counts
            	Collections.sort(fItems);
        		pairs = c.combination(fItems,2);
            	
        		i = pairs.iterator();
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
		
		
		
		// Get all frequent pairs
		Set<List<Integer>> frequentPairs = new HashSet<List<Integer>>();
		Set<Entry<List<Integer>, Integer>> set = candidatePairs.entrySet();

		Iterator<Entry<List<Integer>, Integer>> iterator = set.iterator();
	      // Display elements
	      while(iterator.hasNext()) {
	         Map.Entry me = (Map.Entry)iterator.next();
	         int value = (Integer)me.getValue();
	         if(value > support){
	        	 List<Integer> key = (List<Integer>) me.getKey();
	        	 frequentPairs.add(key);
	         }
	      }
		
		// Get a set of the entries
		System.out.println("================");
		print(candidatePairs);
		System.out.println("========xxx========");
		printSet(frequentPairs);
		
		// PASS 3
		// Generate candidate triples by frequentPairs JOIN frequentPairs

		Set<List<Integer>> allCandidateTriples  = new HashSet<List<Integer>>();
		candidateTriples = new HashMap<List<Integer>, Integer>();
		
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
		System.out.println("==== all CandidateTriples=====");
		printSet(allCandidateTriples);
		
		// Prune non frequent candidate triples 
		for (List<Integer> candiate : allCandidateTriples){
			pairs = c.combination(candiate, 2);
			boolean whatAboutIt = true;
			for(List<Integer> pair: pairs){
				// If this pair is not in frequent pairs then 
				// this candidate will be eliminate from candidate triples
				if(!frequentPairs.contains(pair)){
					whatAboutIt = false;
					break;
				}
			}
			if(whatAboutIt){
				candidateTriples.put(candiate, new Integer(0));
			}
		}
		
		
		System.out.println("===== CandidateTriples =====");
		print(candidateTriples);
		// Get count for candidate triples 
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
		

		// Get frequent triples
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
		
		System.out.println("===== Get Count for CandidateTriples =====");
		print(candidateTriples);
		
		// # Frequent pairs by confidence
		Map<List<Integer>, Double> pairRules = new HashMap<List<Integer>, Double>();
		
		for (List<Integer> pair: frequentPairs){
			
			List<Integer> first = Arrays.asList(pair.get(0));
			List<Integer> second = Arrays.asList(pair.get(1));
			Set<Integer> I = pair.isEmpty()? new HashSet<Integer>() : new HashSet<Integer>(first);
			Set<Integer> J = pair.isEmpty()? new HashSet<Integer>() : new HashSet<Integer>(second);
			
			double conf = confidence(candidateTriples, itemCounts, candidatePairs, I, J);
			pairRules.put(pair, new Double(conf));
			
			conf = confidence(candidateTriples, itemCounts, candidatePairs, J, I);
			pairRules.put(Arrays.asList(pair.get(1), pair.get(0)), conf);
		}
		
		//# Frequent triples by confidence
		
		Map<List<Integer>, Double> tripleRules = new HashMap<List<Integer>, Double>();
		for (List<Integer> triple: frequentTriples){
			
			Set<Integer> triple2 = new HashSet<Integer>(triple);
			Set<List<Integer>> pairs2 = c.combination(triple, 2);
			
			for (List<Integer> pair: pairs2){
				
				Set<Integer> pair2 = new HashSet<Integer>(pair);
				Set<Integer> diff = Sets.difference(triple2, pair2);
				
				double conf = confidence(candidateTriples, itemCounts, candidatePairs, pair2, diff);
				List<Integer> rule = new ArrayList<Integer>();
				rule.addAll(pair);
				rule.addAll(diff);
				tripleRules.put(rule, conf);
				
			}
		}
		
		System.out.println("Done generate rules by confidence");
		
		printRulesByConfidence(pairRules,tripleRules);
		
		/*for (Entry<List<Integer>, Double> pairRule : pairRules.entrySet()){
			
			System.out.println(pairRule.getKey() +"--->" + pairRule.getValue());
		}*/
		
		
    }
    
    static void printRulesByConfidence(Map<List<Integer>, Double> pairRules, Map<List<Integer>, Double> tripleRules ){
    	
    	System.out.println("Pair rules by confidence");
		for (Entry<List<Integer>, Double> pairRule : pairRules.entrySet()){
			System.out.println(pairRule.getKey() +"--->" + pairRule.getValue());
		}
    	System.out.println("Triple rule by confidence");
		for (Entry<List<Integer>, Double> tripleRule : tripleRules.entrySet()){
			System.out.println(tripleRule.getKey() +"--->" + tripleRule.getValue());
		}
    	
    }
    
    static double lift(List<Integer> itemCounts, List<Integer> J, int transactions, double conf){
    	double suppJ = 0;
    	double SJ = 0;
    	
    	if(J.size() == 2){
    		suppJ = itemCounts.get(J.get(1));
    	}else if (J.size() == 3){
    		suppJ = itemCounts.get(J.get(2));
    	}
    	SJ = suppJ / transactions;
    	return (double) conf/SJ;
    }
    
    static double confidence(Map<List<Integer>, Integer> candidateTriples,List<Integer> itemCounts,
    		Map<List<Integer>, Integer> candidatePairs, Set<Integer> I, Set<Integer> J ){
    	
    	int PIJ = 0;
    	int PI = 0;
    	
    	List<Integer> IJ = new ArrayList<Integer>();
    	IJ.addAll(I);
    	IJ.addAll(J);    	
    	Collections.sort(IJ);
    	
    	if(IJ.size() == 2){
    		PIJ = candidatePairs.get(IJ);
    	}else if(IJ.size() == 3){
    		PIJ = candidateTriples.get(IJ);
    	}
    	
    	if(I.size() == 1){
    		int first = I.iterator().next();
    		PI = itemCounts.get(first);
    		
    	}else if (I.size() == 2){
    		List<Integer> sortedI = new ArrayList<Integer>();
    		sortedI.addAll(I);
    		Collections.sort(sortedI);
    		PI = candidatePairs.get(sortedI);
    	}
    	
    	return (double)PIJ/PI;
    }
    
    static void printSet(Set<List<Integer>> collection){
    	for (List<Integer> o : collection){
    		Object[] a = o.toArray();
    		System.out.println(Arrays.toString(a));
    	}
    }
    static void print(Map<List<Integer>, Integer> candidatePairs){
    	 Set<Entry<List<Integer>, Integer>> set = candidatePairs.entrySet();
	      // Get an iterator
    	 Iterator<Entry<List<Integer>, Integer>> i = set.iterator();
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	      }
	      
	      /*for (Map.Entry<List<Integer>, Integer> candiate : allCandidateTriples.entrySet()){
	    	  
	      }*/
    }
    
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
    	Comparator<K> valueComparator = new Comparator<K>() {
		      public int compare(K k1, K k2) {
		        int compare = map.get(k1).compareTo(map.get(k2));
		        if (compare == 0) 
		          return 1;
		        else 
		          return compare;
		      }
    	};
 
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
    }
}
