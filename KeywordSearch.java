import InvertedIndex.*;     
import Trie.*;

import java.util.*;
import java.io.*;

public class KeywordSearch {

	public KeywordSearch() {

	}

	public ArrayList<Integer> union(ArrayList<Integer> l1, ArrayList<Integer> l2) {

		int i = 0;
		int j = 0;
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		int x,y;
		

		while ((!l1.isEmpty() && !l2.isEmpty())  && ((i < l1.size()) && (j < l2.size()))) {
			
			x = l1.get(i);
			y = l2.get(j);
			if (x == y) {
				returnList.add(x);
				++i;
				++j;
			} else if (x < y ) {
				returnList.add(x);
				++i;
			} else {
				returnList.add(y);
				++j;
			}	
		}

		// append rest of list to final list if one was longer
		while (j < l2.size()) {
			y = l2.get(j);
			returnList.add(y);
			++j;
		}
		while (i < l1.size()) {
			x = l1.get(i);
			returnList.add(x);
			++i;
		}
		return returnList;
	}

	
	public ArrayList<Integer> intersection(ArrayList<Integer> l1, ArrayList<Integer> l2) {
		int i = 0;
		int j = 0;
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		int x,y;
		
		while ((!l1.isEmpty() && !l2.isEmpty()) && ((i < l1.size()) && (j < l2.size()))) {
			
			x = l1.get(i);
			y = l2.get(j);
			
			if (x == y) {
				returnList.add(x);
				++i;
				++j;
			} else if (x < y) { 
				++i;
			} else {
				++j;
			}	
		}

		return returnList;
	}
	
	/*
	// function that takes in a term t and document d, and returns the score using tf*idf
	public float score(String t, Integer d) {
		float tf, idf = 0;
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
*/
	 

	public static void main(String[] args) throws FileNotFoundException, IOException {

		KeywordSearch k = new KeywordSearch();
		InvertedIndex i = new InvertedIndex();
		Trie t = new Trie();
		i.parse();
		//i.printTable();
	

		// getting user input as String inputString
		String inputString; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Search the Medline Database: "); 
		inputString = sc.nextLine();  
		sc.close();

		// Creating Trie
		String str;
		Set<String> keys = i.KeyWordsTable.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext()) {
			str = itr.next();
			t.insert(str);
		}
		t.CreateKeywordArray();


		String [] keywords = inputString.split("\\W+"); //splitting inputString into keywords


		// Main function to execute keyword completion and search
		

		ArrayList<Integer> finalList = new ArrayList<Integer>();
		for (String word : keywords) {
			word = word.toLowerCase();
			
			if (i.stopwords.contains(word)) {
				continue;
			} 
			
			ArrayList<String> completedKeywordList = t.findCompletedWordsInTrie(word);
			
			System.out.println("The current keyword is: " + word);
			System.out.println("The completedKeywordList is: " + completedKeywordList);
			System.out.println();
			
			ArrayList<Integer> unionedList = new ArrayList<Integer>();

			
			if (completedKeywordList.size() == 0) {
				unionedList = k.union(i.find(completedKeywordList.get(0)), unionedList );
			}

			// this inner loop unions doc lists for the words in completedKeywordList
			for (int j = 0; j <= completedKeywordList.size() - 1; j++) {
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				//System.out.println("i.find word in inner loop is: " + i.find(completedKeywordList.get(j)));

				tempList = i.find(completedKeywordList.get(j));          //find documents containing the word in this iteration
				Collections.sort(tempList);                              // sort the document list

				System.out.println("The doc tempList for " + completedKeywordList.get(j)+ " is: " + tempList);
				unionedList = k.union(tempList, unionedList);            // union with the other lists
				System.out.println("The unioned List for " + word + " is: " + unionedList);
				System.out.println();
				
			
			}

			if (finalList.isEmpty()) {
				finalList = unionedList;
			} else {
				finalList = k.intersection(finalList, unionedList);
			}
			
			System.out.println("The final list is currently: " + finalList);
			System.out.println();

		}
		
		System.out.println("Search Results: Titles of related articles located below.");
		for (int n = 0; n <= finalList.size() - 1; n++) {
			System.out.println(i.articleTitleIndexToID.get(finalList.get(n)));
		}


	}



}
