package com.abc.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.mycompany.app.Combination;
import com.tmp.Combination2;

public class APrioriServiceImpl<T>  {
	public static void main(String[] args) {
		//Get items from candidates map
		
	}
	public Set<List<String>> readTransactions(String datasource) {
		String basket = "";
		Set<List<String>> transactions = new HashSet<List<String>>();
		try {
			FileReader fileReader = new FileReader(datasource);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((basket = bufferedReader.readLine()) != null) {
				String items[] = basket.split(" ");
				transactions.add(Arrays.asList(items));
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
	
	public Map<Set<T>, Integer> findFrequent1Itemsets(Set<List<T>> transactions, int support){
		
		Map<Set<T>, Integer> candidates = new HashMap<Set<T>, Integer>();
		
		for(List<T> transaction : transactions){
			
			//Using Set collection to avoid duplicate items per transaction
			Set<T> itemSets = new HashSet<T>(transaction);
			for(T item: itemSets){
				Set<T> temp = new HashSet<T>();
				temp.add(item);
				
				if (candidates.containsKey(temp)) {
	                    candidates.put(temp, candidates.get(temp) + 1);
	            } else {
	                    candidates.put(temp, 1);
	            }
			}
		}
		
		//Remove non-frequent candidates basing on support count threshold.
		candidates = eliminateNonFrequentCandidate(candidates, support);
		
		return candidates;
	}

	// Eliminate candidates that are infrequent, leaving only those that are frequent
	private Map<Set<T>, Integer> eliminateNonFrequentCandidate(Map<Set<T>, Integer> candidates, int support) {
		Map<Set<T>, Integer> frequentCandidates = new HashMap<Set<T>, Integer>();

		for (Map.Entry<Set<T>, Integer> candidate : candidates.entrySet()) {
			if (candidate.getValue() >= support) {
				frequentCandidates.put(candidate.getKey(), candidate.getValue());
			}
		}

		return frequentCandidates;
	}
	public Map<Integer, Map<Set<T>, Integer>> generateFrequentItemSets(Set<List<T>> transactions, int support){
		
		Map<Set<T>, Integer> candidateSets = null;
		Map<Set<T>, Integer> candidateSubsets = null;
		Map<Set<T>, Integer> temporary = new HashMap<Set<T>, Integer>();
		Map<Integer, Map<Set<T>, Integer>> candidates = new HashMap<Integer, Map<Set<T>,Integer>>();
		//Find frequent 1 items L1 from a database of transactions
		Map<Set<T>, Integer> frequentItemSets = findFrequent1Itemsets(transactions, support);
		candidates.put(1,frequentItemSets);
		
		for(int k = 2;!frequentItemSets.isEmpty(); k++){
			
			//Generate candidate C(k) from L(k-1)
			candidateSets = aprioriGenerate(frequentItemSets); //C (k)
			
			// Scan transactions for count
			for(List<T> transaction: transactions){
				
				//Get the subsets of transaction that are candidates
				candidateSubsets = subSets(candidateSets, transaction);
				
				for(Set<T> c : candidateSubsets.keySet()){
					//Increase count
					int count = temporary.get(c)== null ? 0 : temporary.get(c) + 1;
					temporary.put(c, count);
				}
			}
			
			frequentItemSets = eliminateNonFrequentCandidate(temporary, support);
			printCandidates(frequentItemSets);
			candidates.put(k,frequentItemSets);
		}
		return candidates;
	}
	
	public Map<Integer, Map<Set<T>, Integer>> generateFrequentItemSets2(Set<List<T>> transactions, int support){
		
		
		
		
		
		return null;
	}
	
	private Set<Set<T>> subSets2(Set<Set<T>> candidateList, List<T> transaction) {

		Set<Set<T>> rs = new HashSet<Set<T>>();
		for (Set<T> candidate : candidateList) {
			List<T> temp = new ArrayList<T>(candidate);
			if (transaction.containsAll(temp)) {
				rs.add(candidate);
			}
		}
		return rs;
	}
	private Map<Set<T>, Integer> subSets(Map<Set<T>, Integer> candidateMap, List<T> transaction) {
		
		Map<Set<T>, Integer> candidateSubSets = new HashMap<Set<T>, Integer>();
		Set<Set<T>> candidateList = candidateMap.keySet();
		
		for(Set<T> candidate: candidateList){
			List<T> temp = new ArrayList<T>(candidate);
			if(transaction.containsAll(temp)){
				candidateSubSets.put(candidate, new Integer(0));
			}
		}
		return candidateSubSets;
	}
	public Map<Set<T>, Integer> aprioriGenerate2(Set<Set<T>> frequentItemSets){
		
		Map<Set<T>, Integer> candidatesGen = new HashMap<Set<T>, Integer>();
		
				
		// Make sure that items within a transaction or itemset are sorted in lexicographic order 
		List<List<T>> sortedList = new ArrayList<List<T>>();
		Set<T> treeSet = null;
		
		for (Set<T> set : frequentItemSets) {
			treeSet = new TreeSet<T>(set);
			sortedList.add(new ArrayList<T>(treeSet));
		}
		
		// Generate itemSet from L(k-1)
		for (int i = 0; i < sortedList.size(); ++i) {
			for (int j = i + 1; j < sortedList.size(); ++j) {
				//Check condition L(k-1) joining with itself 
				if(isJoinable(sortedList.get(i), sortedList.get(j))){
					
					//join step: generate candidates
					Set<T> candidate = tryJoinItemSets(sortedList.get(i),sortedList.get(j));
					
					if(hasFrequentSubSet(candidate,frequentItemSets)){
						
						//Add this candidate to C(k)
						candidatesGen.put(candidate, new Integer(0));
					}
				}
			}
		}  
		return candidatesGen;
	}
	
	public Map<Set<T>, Integer> aprioriGenerate(Map<Set<T>, Integer> candidates){
		
		Map<Set<T>, Integer> candidatesGen = new HashMap<Set<T>, Integer>();
		
		//L(k-1): frequent (k-1) itemsets 
		Set<Set<T>> frequentItemSets = candidates.keySet();
		
		// Make sure that items within a transaction or itemset are sorted in lexicographic order 
		List<List<T>> sortedList = new ArrayList<List<T>>();
		Set<T> treeSet = null;
		
		for (Set<T> set : frequentItemSets) {
			treeSet = new TreeSet<T>(set);
			sortedList.add(new ArrayList<T>(treeSet));
		}
		
		// Generate itemSet from L(k-1)
		for (int i = 0; i < sortedList.size(); ++i) {
			for (int j = i + 1; j < sortedList.size(); ++j) {
				//Check condition L(k-1) joining with itself 
				if(isJoinable(sortedList.get(i), sortedList.get(j))){
					
					//join step: generate candidates
					Set<T> candidate = tryJoinItemSets(sortedList.get(i),sortedList.get(j));
					
					if(hasFrequentSubSet(candidate,frequentItemSets)){
						
						//Add this candidate to C(k)
						candidatesGen.put(candidate, new Integer(0));
					}
				}
			}
		}  
		return candidatesGen;
	}
	
	
	public boolean hasFrequentSubSet(Set<T> candidate, Set<Set<T>> frequentItemSets) {
		Combination<T> c = new Combination<T>();
		List<T> list = new ArrayList<T>(candidate);
		int k = candidate.size()- 1;
		boolean whatAboutIt = true;
		
		//Generate subset s of c candidate
		Set<List<T>> subsets = c.combination(list, k);
		for(List<T> s: subsets){
			Set<T> temp = new HashSet<T>(s);
			if(!frequentItemSets.contains(temp)){
				whatAboutIt = false;
				break;
			}
		}
		return whatAboutIt;
	}
	public Set<T> tryJoinItemSets(List<T> itemSet1, List<T> itemSet2) {

		Set<T> joinItemSets = new HashSet<T>();
		int size = itemSet1.size();
		for (int i = 0; i < size - 1; ++i) {
			joinItemSets.add(itemSet1.get(i));
		}

		joinItemSets.add(itemSet1.get(size - 1)); //Be careful out of index
		joinItemSets.add(itemSet2.get(size - 1)); //Be careful out of index
		
		return joinItemSets;
		
	}
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
	public boolean isJoinable(List<T> list1, List<T> list2) {
		int length = list1.size();

		// Make sure that size of two lists are equal
		if (list1.size() != list2.size())
			return false;

		// Check condition list1[k-1] < list2[k-1] simply ensures that no
		// duplicates are generated
		if (list1.get(length - 1).equals(list2.get(length - 1))) {
			return false;
		}

		// Check members of list1 and list2 are joined if condition list1[k-2] =
		// list2[k-2]
		for (int k = 0; k < length - 1; k++) {
			if (!list1.get(k).equals(list2.get(k))) {
				return false;
			}
		}

		return true;
	}
	
	public void printCandidates(Map<Set<T>, Integer> candidates){
		for (Map.Entry<Set<T>, Integer> candidate : candidates.entrySet()){
			System.out.println(candidate.getKey()+ " " + candidate.getValue());
		}
	}
}
