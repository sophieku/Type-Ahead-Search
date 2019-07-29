import InvertedIndex.*;     // make sure can import packages

//import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.Arrays; 
import java.util.Collections;

public class KeywordSearch {


	public ArrayList<Integer> union(ArrayList<Integer> l1, ArrayList<Integer> l2) {
	
		//union block of Keyword Search
		int i = 0;
		int j = 0;
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		
		while (l1.get(i) != null && l2.get(j) != null) {
			if (l1.get(i) == l2.get(i)) {
				returnList.add(l1.get(i));
				++i;
				++j;
			} else if (l1.get(i) < l2.get(i) ) {
				returnList.add(l1.get(i));
				++i;
			} else {
				returnList.add(l2.get(j));
				++j;
			}	
		}
		
		// append rest of list to final list if one was longer
		if ( l1.get(i) == null) {
			while (l2.get(j) != null) {
				returnList.add(l2.get(j));
				++j;
			}
		} else if (l2.get(j) == null) {
			while (l2.get(j) != null) {
				returnList.add(l2.get(j));
				++j;
			}
		}
	}

	public  ArrayList<Integer> intersection(ArrayList<Integer> l1, ArrayList<Integer> l2) {
		while (l1[i] != null && l2[j] != null) {
			if (l1[i].getKey() == l2[j].getKey()) {
				finalList[i] = l1[i].getKey();
				++i;
				++j;
			} else if (l1[i].getKey()< l2[j].getKey() ) { 
				++i;
			} else {
				++j;
			}	
		}
	}

	// function that takes in a term t and document d, and returns the score using tf*idf
	public float score(String t, Integer d) {
		float tf, idf = 0.0;
		Pair<Integer, Integer> p = KeyWordsTable.get(t);

		//finding tf
		if (p.getValue() > 0) {
			tf =  1 + Math.log10(p.getValue())
		} else {
			tf =  0;
		}

		//finding idf
		idf = Math.log10((docId) / (1 + p.getValue()); //find out how to get largest doc ID from invertedIndex.java 

	}



	public static void main(String[] args) {
		
		InvertedIndex i = new InvertedIndex();
		i.parse();

		// getting user input as String inputString
		String inputString; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Search the Medline Database: "); 
		inputString = sc.nextLine();  
		sc.close();
		
		
		String [] keywords = inputString.split("\\W+"); //splitting inputString into keywords
		
		
		// Main function to execute keyword completion and search
		
		ArrayList<Integer> finalList = new ArrayList<Integer>();
		for (String word : keywords) {
			word = word.toLowerCase();
			
			//for now, assume keywordCompletion(word) will return a list of words that complete the keyword
			ArrayList<String> completedKeywordList = i.keywordCompletion(word);
			
			ArrayList<Integer> unionedList = new ArrayList<Integer>();
			// this loop unions keyword completion documents in a sorted order
			for (int j = 0; j < completedKeywordList.size() - 1; j++) {
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				tempList = i.find(completedKeywordList.get(j));
				Collections.sort(tempList);
				unionedList = union(tempList, unionedList);
			}
			
			finalList = intersection(finalList, unionedList);
			
			
		}
		
		

	}



}
