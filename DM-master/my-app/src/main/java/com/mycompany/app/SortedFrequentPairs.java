package com.mycompany.app;

import java.util.List;

public class SortedFrequentPairs implements Comparable<List<Integer>> {
	private List<Integer> arg;
	
	public List<Integer> getArg() {
		return arg;
	}

	public int compareTo(List<Integer> compareArg) {
		int size = compareArg.size();
		int i = 0;
					
		while (arg.get(i).equals(compareArg.get(i)) && i<size){
			i++;
		}
		return arg.get(i) - compareArg.get(i);
	}

}
