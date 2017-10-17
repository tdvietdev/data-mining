package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private  Map<List<Integer>, Integer> candidateTriples;
	private  List<Integer> itemCounts;
	private static Map<List<Integer>, Integer> candidatePairs;
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {   
    	super( testName );
    	confidenceInitialData();
    }
    public void confidenceInitialData(){
    	candidatePairs = new HashMap<List<Integer>, Integer>();
    	candidatePairs.put(Arrays.asList(0,1), new Integer(3));
    	candidatePairs.put(Arrays.asList(1,3), new Integer(2));
    	candidatePairs.put(Arrays.asList(1,4), new Integer(3));
    	candidatePairs.put(Arrays.asList(0,4), new Integer(2));
    	candidatePairs.put(Arrays.asList(0,3), new Integer(1));
    	candidatePairs.put(Arrays.asList(3,4), new Integer(3));
    	
    	candidateTriples = new HashMap<List<Integer>, Integer>();
    	candidateTriples.put(Arrays.asList(0,1,4), new Integer(2));
    	candidateTriples.put(Arrays.asList(1,3,4), new Integer(2));
    	
    	itemCounts = Arrays.asList(3,4,1,3,4);
    	
    }
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testConfidence()
    {
    	Set<Integer> I = new HashSet<Integer>();
    	I.addAll(Arrays.asList(0));
    	Set<Integer> J = new HashSet<Integer>();
    	J.addAll(Arrays.asList(1));
    	double result = App.confidence(candidateTriples, itemCounts, candidatePairs, I, J);
    	assertEquals(1.0, result, 0);
    	
    	I.clear();
    	J.clear();
    	I.addAll(Arrays.asList(1));
    	J.addAll(Arrays.asList(0));
    	result = App.confidence(candidateTriples, itemCounts, candidatePairs, I, J);
    	assertEquals(0.75, result, 2);
    	
    	I.clear();
    	J.clear();
    	I.addAll(Arrays.asList(0));
    	J.addAll(Arrays.asList(4));
    	result = App.confidence(candidateTriples, itemCounts, candidatePairs, I, J);
    	assertEquals(0.6666666666666666, result, 16);

    	I.clear();
    	J.clear();
    	I.addAll(Arrays.asList(0,1));
    	J.addAll(Arrays.asList(4));
    	result = App.confidence(candidateTriples, itemCounts, candidatePairs, I, J);
    	assertEquals(0.6666666666666666, result, 16);
    	
    	I.clear();
    	J.clear();
    	I.addAll(Arrays.asList(0,4));
    	J.addAll(Arrays.asList(1));
    	result = App.confidence(candidateTriples, itemCounts, candidatePairs, I, J);
    	assertEquals(1.0, result, 1);
    	
    }
}
