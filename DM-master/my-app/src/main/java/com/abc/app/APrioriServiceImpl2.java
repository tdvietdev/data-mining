package com.abc.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.mycompany.app.Combination;

public class APrioriServiceImpl2<T>  {
	
	

	public  List<List<T>> sortList(List<Set<T>> frequentItemSets){
		
		List<List<T>> list = new ArrayList<List<T>>();
		Set<T> treeSet = null;
		for(Set<T> item: frequentItemSets){
			treeSet = new TreeSet<T>(item) ;
			list.add(new ArrayList<T>(treeSet));
		}
		return list;
	}
	
	public static void main(String[] args) {
		
		/*APrioriServiceImpl2<String> serviceImpl = new APrioriServiceImpl2<String>();
		List<List<String>> list = new ArrayList<List<String>>();
		List<Set<String> > frequentItemSets = new ArrayList<Set<String>>();
		frequentItemSets.add(new HashSet<String>(Arrays.asList("I4","I2","I0")));
		frequentItemSets.add(new HashSet<String>(Arrays.asList("I9","I5","I7")));
		frequentItemSets.add(new HashSet<String>(Arrays.asList("I5")));
		
		List<List<String>> c = new ArrayList<List<String>>();
		
		c =  serviceImpl.sortList(frequentItemSets);
		
		
		for (List<String> l: c){
			System.out.println(l);
		}
		*/
		
		List<Set<String>> itemsetList = new ArrayList<Set<String>>();

        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I2","I5")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I2", "I4")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I2", "I3")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I2","I4")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I3")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I2", "I3")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I3")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I2","I3","I5")));
        itemsetList.add(new HashSet<String>(Arrays.asList("I1", "I2","I3")));
        
		APrioriServiceImpl2<String> serviceImpl = new APrioriServiceImpl2<String>();
		serviceImpl.generateFrequentItemSets(itemsetList, 2);
		
	}
	/*public List<Set<String>> readTransactions(String datasource) {
		String basket = "";
		List<Set<String>> transactions = new ArrayList<Set<String>>();
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
	}*/
	
	public List<Set<T>> findFrequent1Itemsets(List<Set<T>> transactions, int support){
		
		Map<Set<T>, Integer> candidates = new HashMap<Set<T>, Integer>();
		
		for(Set<T> transaction : transactions){
			
			//Using Set collection to avoid duplicate items per transaction
			for(T item: transaction){
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
		return eliminateNonFrequentCandidate(candidates, support);
	}

	// Eliminate candidates that are infrequent, leaving only those that are frequent
	private List<Set<T>> eliminateNonFrequentCandidate(Map<Set<T>, Integer> candidates, int support) {
		List<Set<T>> frequentCandidates = new ArrayList<Set<T>>();

		for (Map.Entry<Set<T>, Integer> candidate : candidates.entrySet()) {
			if (candidate.getValue() >= support) {
				frequentCandidates.add(candidate.getKey());
			}
		}

		return frequentCandidates;
	}

	
	public T generateFrequentItemSets(List<Set<T>> transactionList, int support) {

		Map<Set<T>, Integer> supportCountMap = new HashMap<Set<T>, Integer>();
		
		//Find all frequent 1-itemsets
		List<Set<T>> frequentItemList = findFrequent1Itemsets(transactionList, support);
		
		Map<Integer, List<Set<T>>> map = new HashMap<Integer, List<Set<T>>>();
		map.put(1, frequentItemList);

		int k = 1;
		
		for (k = 2; !map.get(k-1).isEmpty(); k++) {
			
			// First generate the candidates.
			List<Set<T>> candidateList = aprioriGenerate(map.get(k - 1));
			// Scan D for counts
			for (Set<T> transaction : transactionList) {
				//Get the subsets of t that are candidates
				List<Set<T>> candidateList2 = subSets(candidateList, transaction);

				for (Set<T> itemset : candidateList2) {
					//Increase support count
					int count = supportCountMap.get(itemset) == null ? 1 : supportCountMap.get(itemset) + 1;
					supportCountMap.put(itemset, count);
				}
			}
			map.put(k, extractNextFrequentCandidates(candidateList, supportCountMap, support));
		}
		
		//print map
		for (List<Set<T>> itemsetList : map.values()) {
			for(Set<T> itemset: itemsetList){
				System.out.println(itemset);
			}
		}
		
		return null;
	}

	private List<Set<T>> extractNextFrequentCandidates(List<Set<T>> candidateList, Map<Set<T>, Integer> supportCountMap,
			int support) {

		List<Set<T>> rs = new ArrayList<Set<T>>(candidateList.size());

		for (Set<T> itemset : candidateList) {
			if (supportCountMap.containsKey(itemset)) {
				int supportCount = supportCountMap.get(itemset);
				if (supportCount >= support) {
					rs.add(itemset);
				}
			}
		}

		return rs;
	}
	
	private List<Set<T>> subSets(List<Set<T>> candidateList, Set<T> transaction) {

		List<Set<T>> rs = new ArrayList<Set<T>>();
		for (Set<T> candidate : candidateList) {
			List<T> temp = new ArrayList<T>(candidate);
			if (transaction.containsAll(temp)) {
				rs.add(candidate);
			}
		}
		return rs;
	}
	
	public List<Set<T>> aprioriGenerate(List<Set<T>> frequentItemSets){
		
		List<Set<T>> candidatesGen = new ArrayList<Set<T>>();
		
		// Make sure that items within a transaction or itemset are sorted in lexicographic order 
		List<List<T>> sortedList = sortList(frequentItemSets);
		
		// Generate itemSet from L(k-1)
		for (int i = 0; i < sortedList.size(); ++i) {
			for (int j = i + 1; j < sortedList.size(); ++j) {
				//Check condition L(k-1) joining with itself 
				if(isJoinable(sortedList.get(i), sortedList.get(j))){
					
					//join step: generate candidates
					Set<T> candidate = tryJoinItemSets(sortedList.get(i),sortedList.get(j));
					
					if(hasFrequentSubSet(candidate,frequentItemSets)){
						
						//Add this candidate to C(k)
						candidatesGen.add(candidate);
					}
					
				}
			}
		}  
		return candidatesGen;
	}
	

	
	public boolean hasFrequentSubSet(Set<T> candidate, List<Set<T>> frequentItemSets) {
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

		Set<T> joinItemSets = new TreeSet<T>();
		int size = itemSet1.size();
		for (int i = 0; i < size - 1; ++i) {
			joinItemSets.add(itemSet1.get(i));
		}
		joinItemSets.add(itemSet1.get(size - 1)); 
		joinItemSets.add(itemSet2.get(size - 1)); 
				
		return joinItemSets;
		
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
