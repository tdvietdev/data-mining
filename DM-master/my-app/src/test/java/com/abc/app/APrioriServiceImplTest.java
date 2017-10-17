package com.abc.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import junit.framework.Assert;

public class APrioriServiceImplTest {
	
	private APrioriServiceImpl<String> serviceImpl = null;
	
	public APrioriServiceImplTest() {
		serviceImpl = new APrioriServiceImpl<String>();
	}
	
	@Test
	public void isJoinableTest(){
		
		List<String> list1 = new ArrayList<String>(Arrays.asList("a","b"));
		List<String> list2 = new ArrayList<String>(Arrays.asList("a"));
		
		boolean actual = serviceImpl.isJoinable(list1, list2);
		Assert.assertEquals(false, actual);
		
		
		List<String> list3 = new ArrayList<String>(Arrays.asList("a","b"));
		List<String> list4 = new ArrayList<String>(Arrays.asList("a","b"));
		actual = serviceImpl.isJoinable(list3, list4);
		Assert.assertEquals(false, actual);
		
		
		List<String> list5 = new ArrayList<String>(Arrays.asList("a","b","c","d"));
		List<String> list6 = new ArrayList<String>(Arrays.asList("a","b","f","e"));
		actual = serviceImpl.isJoinable(list5, list6);
		Assert.assertEquals(false, actual);
		
		List<String> list7 = new ArrayList<String>(Arrays.asList("a","b","c","d"));
		List<String> list8 = new ArrayList<String>(Arrays.asList("a","b","f","e"));
		actual = serviceImpl.isJoinable(list7, list8);
		Assert.assertEquals(false, actual);
		
		List<String> list9 = new ArrayList<String>(Arrays.asList("a","b","c","d"));
		List<String> list10 = new ArrayList<String>(Arrays.asList("a","b","c","e"));
		actual = serviceImpl.isJoinable(list9, list10);
		Assert.assertEquals(true, actual);
		
	}
	@Test
	public void readTransactionsTest(){
		
		Set<List<String>> transactions = serviceImpl.readTransactions("C:/Users/khiemT/Desktop/baskets2.txt");
		Assert.assertEquals(8, transactions.size());
		
		//Test duplicate transactions
		transactions = serviceImpl.readTransactions("C:/Users/khiemT/Desktop/baskets3.txt");
		Assert.assertEquals(8, transactions.size());
		
	}
	
	@Test
	public void findFrequent1ItemsetsTest(){
		
		Set<List<String>> transactions = serviceImpl.readTransactions("C:/Users/khiemT/Desktop/baskets2.txt");
		
		Map<Set<String>, Integer> candidates = serviceImpl.findFrequent1Itemsets(transactions, 3);
		//serviceImpl.printCandidates(candidates);
		
		
	}
	
	@Test
	public void generateFrequentItemSetsTest(){
		Set<List<String>> transactions = serviceImpl.readTransactions("C:/Users/khiemT/Desktop/baskets2.txt");
		serviceImpl.generateFrequentItemSets(transactions,3);
	}
	
	@Test
	public void joinItemSetsTest(){
		
		List<String> list1 = Arrays.asList("a","b");
		List<String> list2 = Arrays.asList("a","c");
		Set<String> joinItemSets = serviceImpl.tryJoinItemSets(list1,list2);
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a","b","c")), joinItemSets);
		
		List<String> list3 = Arrays.asList("a");
		List<String> list4 = Arrays.asList("b");
		joinItemSets = serviceImpl.tryJoinItemSets(list3,list4);
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a","b")), joinItemSets);
		
		List<String> list5 = Arrays.asList("a","b","c");
		List<String> list6 = Arrays.asList("a","b","d");
		joinItemSets = serviceImpl.tryJoinItemSets(list5,list6);
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a","b","c","d")), joinItemSets);

	}
	
	@Test
	public void hasFrequentSubSetTest(){
		Set<String> candidate = null;
		boolean actual = true;
		Set<Set<String>> frequentitemSets = new HashSet<Set<String>>();
		Set<String> item1 = new HashSet<String>(Arrays.asList("b","m"));
		Set<String> item2 = new HashSet<String>(Arrays.asList("b","c"));
		Set<String> item3 = new HashSet<String>(Arrays.asList("c","m"));
		Set<String> item4 = new HashSet<String>(Arrays.asList("c","j"));
		frequentitemSets.add(item1);
		frequentitemSets.add(item2);
		frequentitemSets.add(item3);
		frequentitemSets.add(item4);
		
		candidate = new HashSet<String>(Arrays.asList("b","c","m"));
		actual = serviceImpl.hasFrequentSubSet(candidate,frequentitemSets); 
		Assert.assertEquals(true, actual);
		
		candidate = new HashSet<String>(Arrays.asList("b","c","j"));
		actual = serviceImpl.hasFrequentSubSet(candidate,frequentitemSets); 
		Assert.assertEquals(false, actual);
		
		candidate = new HashSet<String>(Arrays.asList("b","m","j"));
		actual = serviceImpl.hasFrequentSubSet(candidate,frequentitemSets); 
		Assert.assertEquals(false, actual);
		
		candidate = new HashSet<String>(Arrays.asList("c","m","j"));
		actual = serviceImpl.hasFrequentSubSet(candidate,frequentitemSets); 
		Assert.assertEquals(false, actual);
	}
	
	@Test
	public void aprioriGenerateTest(){
		Map<Set<String>, Integer> candidates = null;
		Map<Set<String>, Integer> actual = null;
		Set<Set<String>> candidateList = null;
		
		//Generate pair items
		candidates = new HashMap<Set<String>, Integer>();
		candidates.put(new HashSet<String>(Arrays.asList("b")), new Integer(6));
		candidates.put(new HashSet<String>(Arrays.asList("c")), new Integer(6));
		candidates.put(new HashSet<String>(Arrays.asList("m")), new Integer(5));
		candidates.put(new HashSet<String>(Arrays.asList("j")), new Integer(4));
		
		actual = serviceImpl.aprioriGenerate(candidates);
		Assert.assertEquals(6, actual.size());
		
		candidateList = actual.keySet();
		Set<String> c1 = new HashSet<String>(Arrays.asList("b","c"));
		Set<String> c2 = new HashSet<String>(Arrays.asList("b","j"));
		Set<String> c3 = new HashSet<String>(Arrays.asList("b","m"));
		Set<String> c4 = new HashSet<String>(Arrays.asList("c","j"));
		Set<String> c5 = new HashSet<String>(Arrays.asList("c","m"));
		Set<String> c6 = new HashSet<String>(Arrays.asList("j","m"));
		Assert.assertEquals(true, candidateList.contains(c1));
		Assert.assertEquals(true, candidateList.contains(c2));
		Assert.assertEquals(true, candidateList.contains(c3));
		Assert.assertEquals(true, candidateList.contains(c4));
		Assert.assertEquals(true, candidateList.contains(c5));
		Assert.assertEquals(true, candidateList.contains(c6));
		
		//Generate triple items
		candidates = new HashMap<Set<String>, Integer>();
		candidates.put(new HashSet<String>(Arrays.asList("b","m")), new Integer(0));
		candidates.put(new HashSet<String>(Arrays.asList("b","c")), new Integer(0));
		candidates.put(new HashSet<String>(Arrays.asList("c","m")), new Integer(0));
		candidates.put(new HashSet<String>(Arrays.asList("c","j")), new Integer(0));
		actual = serviceImpl.aprioriGenerate(candidates);
		Assert.assertEquals(1, actual.size());
		
		candidateList = actual.keySet();
		Set<String> c7 = new HashSet<String>(Arrays.asList("b","c","m"));
		Set<String> c8 = new HashSet<String>(Arrays.asList("b","c","j"));
		Set<String> c9 = new HashSet<String>(Arrays.asList("b","m","j"));
		Set<String> c10 = new HashSet<String>(Arrays.asList("c","m","j"));
		Assert.assertEquals(true, candidateList.contains(c7));
		Assert.assertEquals(false, candidateList.contains(c8));
		Assert.assertEquals(false, candidateList.contains(c9));
		Assert.assertEquals(false, candidateList.contains(c10));
		
		
	}
	
}
