package com.abc.app;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ServiceImplTest {
	
	@Test
	public void testServiceImpl() {
		String data = "C:/Users/khiemT/Desktop/baskets2.txt";
		ServiceImpl serviceImpl = new ServiceImpl();
		int minSupport = 3;
		int k = 1;
		Map<List<String>, Integer> countCandidates = serviceImpl.countCandidate(data);
		System.out.println("Count Candidate C1");
		printCandidates(countCandidates);
		
		Map<List<String>, Integer> frequentCandidates = serviceImpl.eliminateNonFrequentCandidate(countCandidates, minSupport); //THE SAME
		System.out.println("Frequent Candidate L1");
		printCandidates(frequentCandidates);
		
		/*List<String> frequentItems = serviceImpl.getSortedFrequentCandidates(frequentCandidates);
		System.out.println("Sorted items:"+frequentItems);*/
		k = 2;
		Map<List<String>, Integer> generateCandidates = serviceImpl.generateCandidate(frequentCandidates,k);
		System.out.println("Generate Candidate C2");
		printCandidates(generateCandidates);
		
		Map<List<String>, Integer> countCandidates2 = serviceImpl.countCandidates2(generateCandidates, data, k);
		System.out.println("Count Candidate C" + k );
		printCandidates(countCandidates2);
		
		Map<List<String>, Integer> frequentCandidates2 = serviceImpl.eliminateNonFrequentCandidate(countCandidates2, minSupport); // THE SAME
		System.out.println("Frequent Candidate L" + k);
		printCandidates(frequentCandidates2);
		
	}
	
	public void printCandidates(Map<List<String>, Integer> candidates){
		for (Map.Entry<List<String>, Integer> candidate : candidates.entrySet()){
			System.out.println(candidate.getKey()+ " " + candidate.getValue());
		}
	}
}
