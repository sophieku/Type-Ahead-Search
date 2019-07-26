import InvertedIndex.*;     // make sure can import packages
import java.util.*;
import java.io.*;
import java.lang.*;

public class KeywordSearch {

	public void quickSort(List<Pair <Integer, Integer>> list, int begin, int end) {
		if (begin < end) {
			int partitionIndex = partition(list, begin, end);

			quickSort(list, begin, partitionIndex-1);
			quickSort(list, partitionIndex+1, end);
		}
	}

	private int partition(List<Pair <Integer, Integer>> list, int begin, int end) {
		int pivot = list[end];
		int i = (begin-1);

		for (int j = begin; j < end; j++) {
			if (list[j] <= pivot) {
				i++;

				int swapTemp = list[i];
				list[i] = list[j];
				list[j] = swapTemp;
			}
		}

		int swapTemp = arr[i+1];
		list[i+1] = list[end];
		list[end] = swapTemp;

		return i+1;
	}

	public List union(List l1, List l2) {
		List finalList;


		//union block of Keyword Search
		int i = 0;
		int j = 0;
		//for (int k = 0; k < (l1.length() + l2.length()) {
		while (l1[i] != null && l2[j] != null) {
			if (l1[i].getKey() == l2[j].getKey()) {
				finalList[i] = l1[i].getKey();
				++i;
				++j;
			} else if (l1[i].getKey()< l2[j].getKey() ) {
				finalList[i] = l1[i].getKey(); 
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



	public static void main {
		// get input word from user, save as searchString
		// parse string into keywords
		// find children trienodes that have isEndOfWord == true and save in matchingKeywordsList
		// parse through matchingKeywordsList
		// List trieChildrenList = find(word in list);
		
		InvertedIndex i = new InvertedIndex();

		i.parse();

		String inputString; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Search the Medline Database: "); 
		inputString = sc.nextLine();  
		
		String [] keywords = inputString.split("\\W+");
		//System.out.println(keywords);
		for (String word : inputString) {
			word = word.toLowerCase();
			i.find(s);
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
