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



		while ((!l1.isEmpty() && !l2.isEmpty())  && ((i < l1.size()) && (j < l2.size()))) {
			if (l1.get(i) == l2.get(j)) {
				returnList.add(l1.get(i));
				++i;
				++j;
			} else if (l1.get(i) < l2.get(j) ) {
				returnList.add(l1.get(i));
				++i;
			} else {
				returnList.add(l2.get(j));
				++j;
			}	
		}

		// append rest of list to final list if one was longer
		while (j < l2.size()) {
			returnList.add(l2.get(j));
			++j;
		}
		while (i < l1.size()) {
			returnList.add(l1.get(i));
			++i;
		}
		return returnList;
	}

	
	public ArrayList<Integer> intersection(ArrayList<Integer> l1, ArrayList<Integer> l2) {

		int i = 0;
		int j = 0;
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		
		
		while ((!l1.isEmpty() && !l2.isEmpty()) && ((i < l1.size()) && (j < l2.size()))) {
			if (l1.get(i) == l2.get(j)) {
				returnList.add(l1.get(i));
				++i;
				++j;
			} else if (l1.get(i) < l2.get(j) ) { 
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

	 */

	public static void main(String[] args) throws FileNotFoundException, IOException {

		KeywordSearch k = new KeywordSearch();
		InvertedIndex i = new InvertedIndex();
		Trie t = new Trie();
		i.parse();
		//i.printTable();
		System.out.println(i.find("subepidermal"));


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

			ArrayList<String> completedKeywordList = t.findCompletedWordsInTrie(word);
			System.out.println("completedKeywordList is: " + completedKeywordList);

			ArrayList<Integer> unionedList = new ArrayList<Integer>();
			//unionedList.add(null);
			
			if (completedKeywordList.size() == 0) {
				unionedList = k.union(i.find(completedKeywordList.get(0)), unionedList );
			}

			// this inner loop unions doc lists for the words in completedKeywordList
			for (int j = 0; j <= completedKeywordList.size() - 1; j++) {
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				System.out.println("word in inner loop is: " + completedKeywordList.get(j));
				//System.out.println("i.find word in inner loop is: " + i.find(completedKeywordList.get(j)));

				tempList = i.find(completedKeywordList.get(j));          //find documents containing the word in this iteration
				Collections.sort(tempList);                              // sort the document list

				System.out.println("tempList is: " + tempList);
				unionedList = k.union(tempList, unionedList);            // union with the other lists
				System.out.println("The unioned List for " + word + " is now : " + unionedList);
				System.out.println();
				
			
			}

			if (finalList.isEmpty()) {
				finalList = unionedList;
			}
			
			finalList = k.intersection(finalList, unionedList);
			System.out.println("The final list is: " + finalList);

		}



	}



}
