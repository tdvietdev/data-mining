package com.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/hmkcode/Java/blob/master/java-combinations/Combination.java
 * @author hmkcode
 *
 */

public class Combination2 {

	
	public static void main(String[] args){
		//Object[] objects = new Object[] {0,1,3,4};
		
		List<String> elements = new ArrayList<String>();
		elements.add("a");
		elements.add("b");
		elements.add("c");
		elements.add("d");
		
		Combination2 c = new Combination2();
		Set<List<String>> sets = c.combination(elements,3);
		
		
		Iterator<List<String>> i = sets.iterator();
		// Display elements
		while (i.hasNext()) {
			System.out.println(i.next());
		}
	}
	
	
	public Set<List<String>> combination(List<String>  elements, int K){

		Set<List<String>> sets = new HashSet<List<String>>();

		
		// get the length of the array
		// e.g. for {'A','B','C','D'} => N = 4 
		int N = elements.size();
		
		if(K > N){
			System.out.println("Invalid input, K > N");
			return sets;
		}
		// calculate the possible combinations
		// e.g. c(4,2)
		//c(N,K);
		
		// get the combination by index 
		// e.g. 01 --> AB , 23 --> CD
		int combination[] = new int[K];
		
		// position of current index
		//  if (r = 1)				r*
		//	index ==>		0	|	1	|	2
		//	element ==>		A	|	B	|	C
		int r = 0;		
		int index = 0;
		
		while(r >= 0){
			// possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
			// possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"
			
			// for r = 0 ==> index < (4+ (0 - 2)) = 2
			if(index <= (N + (r - K))){
					combination[r] = index;
					
				// if we are at the last position print and increase the index
				if(r == K-1){

					//do something with the combination e.g. add to list or print
					//print(combination, elements);
					List<String> list= getCombination(combination,elements);
					sets.add(list);
					
					index++;				
				}
				else{
					// select index for next position
					index = combination[r]+1;
					r++;										
				}
			}
			else{
				r--;
				if(r > 0)
					index = combination[r]+1;
				else
					index = combination[0]+1;	
			}			
		}
		return sets;
	}
	

	
	private List<String> getCombination(int[] combination, List<String> elements) {
		List<String> arrays = new ArrayList<String>();
		for(int z = 0 ; z < combination.length;z++){
			arrays.add(elements.get(combination[z]));
		}
		//System.out.println(arrays);
		return arrays;
	}


	public static int c(int n, int r){
		int nf=fact(n);
		int rf=fact(r);
		int nrf=fact(n-r);
		int npr=nf/nrf;
		int ncr=npr/rf; 
		
		System.out.println("C("+n+","+r+") = "+ ncr);

		return ncr;
	}
	
	public static int fact(int n)
	{
		if(n == 0)
			return 1;
		else
			return n * fact(n-1);
	}
	
}