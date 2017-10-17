package com.abc.app;

public interface APrioriService {
	public void readBaskets(String data);
	public void countSupport();
	public void pruneNonFrequent();
	public void generateFrequentItemSets();
	
}
