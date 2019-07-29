import InvertedIndex.*;     // make sure can import packages
import java.util.*;
import java.io.*;
import java.util.Arrays; 
//import java.lang.*;

public class KeywordSearch {


	public List union(ArrayList<Integer> l1, ArrayList<Integer> l2) {
		ArrayList<Integer> finalList = new ArrayList<Integer>();


		//union block of Keyword Search
		int i = 0;
		int j = 0;
		
		while (l1.get(i) != null && l2.get(i) != null) {
			if (l1.get(i) == l2.get(i)) {
				finalList.add(l1.get(i));
				++i;
				++j;
			} else if (l1.get(i)< l2.get(i) ) {
				finalList.add(l1.get(i));
				++i;
			} else {
				finalList[i] = l2[j].getKey();
				++j;
			}	
		}
		// append rest of list to final list if one was longer
		if ( l1[i] == null) {

		}
	}

	public List intersection(List l1, List l2) {
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
		
		// find children trienodes that have isEndOfWord == true and save in matchingKeywordsList
		// parse through matchingKeywordsList
		// List trieChildrenList = find(word in list);
		
		InvertedIndex i = new InvertedIndex();
		i.parse();

		// getting user input as String inputString
		String inputString; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Search the Medline Database: "); 
		inputString = sc.nextLine();  
		
		// Finding docs from inverted index and sorting using quick sort
		String [] keywords = inputString.split("\\W+");
		int[] doclist;
		int counter = 0;
		for (String word : keywords) {
			word = word.toLowerCase();
			i.find(word);
			counter++;
		}
		
		sc.close();
		
		quickSort(trieChildrenList, 0, trieChildrenList.length());

		// union all completed nodes into one sorted list (union function sorts them)
		// for loop to union all keywords?
		List unionedNodesList = new ArrayList();
		while (trieChildrenList[i+1] != null) {
			if (i == 0) {
				unionedNodesList = trieChildrenList[i];
			}
			unionedNodesList = union(unionedNodesList, trieChildrenList[i+1]);
			++i;
		}

		//Intersect unioned lists
		while 

	}







}
