import InvertedIndex.*;     
import Trie.*;
import Pair.*;
import java.util.*;
import java.io.*;

public class KeywordSearch {

	public KeywordSearch() {

	}

	public ArrayList<Integer> union(ArrayList<Integer> l1, ArrayList<Integer> l2) {

		int i = 0;
		int j = 0;
		int x,y;
		ArrayList<Integer> returnList = new ArrayList<Integer>();

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


	// function that takes in a term t and document d, and returns the score using tf*idf
	public double score(String t, Integer d, InvertedIndex i) {
		double tf = 0;
		double idf;

		List <Pair <Integer, Integer> > wordToDocList = i.KeyWordsTable.get(t);
		
		// finding raw count for term frequency
		for (int idx = 0; idx <= wordToDocList.size() - 1; idx++) {
			int docNum = wordToDocList.get(idx).getFirst();
			if (docNum == d) {
				tf = wordToDocList.get(idx).getSecond();
				break;
			}
		}
		
		//finding tf
		if (tf > 0) {
			tf =  1.0 + Math.log10(tf);
		} 

		//finding idf
		int numDocsThatContainKeyword = wordToDocList.size();
		int totalNumDocsInCorpus = i.articleTitleIndexToID.size();
		idf = Math.log10((totalNumDocsInCorpus) / (1 + numDocsThatContainKeyword)); //find out how to get largest doc ID from invertedIndex.java
		return (tf * idf);
	
	}


	public ArrayList<Integer> rankDocuments(String[] keywords, ArrayList<Integer> unrankedList, Trie t, InvertedIndex i) {

		ArrayList <Pair <Integer, Double>> docScoreList = new ArrayList <Pair <Integer, Double>>();
		for (int n = 0; n <= unrankedList.size() - 1; n++) {
			Double docScore = 0.0;
			Integer docNumber = unrankedList.get(n);
			for (String word : keywords) {
				ArrayList<String> completedKeywordList = t.findCompletedWordsInTrie(word);
				for (int j = 0; j <= completedKeywordList.size() - 1; j++) {
					docScore = docScore + score(completedKeywordList.get(j), docNumber, i);
				}
			}
			System.out.println("The docScore for document " + docNumber + " is:  " + docScore);
			Pair<Integer, Double> scoreNumPair = Pair.createPair(docNumber, docScore);
			docScoreList.add(scoreNumPair);
		}
		
		Collections.sort(docScoreList, new sortByScore());
		
		
		ArrayList<Integer> rankedDocsList = new ArrayList<Integer>();
		for (int n = 0; n <= docScoreList.size() - 1; n++) {
			// getting doc number from doc score list and adding to rankedDocsList
			rankedDocsList.add(docScoreList.get(n).getFirst()); 
		}
		
		return rankedDocsList;
	}



	public static void main(String[] args) throws FileNotFoundException, IOException {

		KeywordSearch k = new KeywordSearch();
		Trie t = new Trie();

		InvertedIndex i = new InvertedIndex();
		i.parse();



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

			//System.out.println("The current keyword is: " + word);
			//System.out.println("The completedKeywordList is: " + completedKeywordList);
			System.out.println();

			ArrayList<Integer> unionedList = new ArrayList<Integer>();


			if (completedKeywordList.isEmpty()) {
				//unionedList = k.union(i.find(completedKeywordList.get(0)), unionedList );
				continue;
			}

			// this inner loop unions doc lists for the words in completedKeywordList
			for (int j = 0; j <= completedKeywordList.size() - 1; j++) {
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				//System.out.println("i.find word in inner loop is: " + i.find(completedKeywordList.get(j)));

				tempList = i.find(completedKeywordList.get(j));          //find documents containing the word in this iteration
				Collections.sort(tempList);                              // sort the document list

				//System.out.println("The doc tempList for " + completedKeywordList.get(j)+ " is: " + tempList);
				unionedList = k.union(tempList, unionedList);            // union with the other lists
				//System.out.println("The unioned List for " + word + " is: " + unionedList);
				//System.out.println();


			}

			if (finalList.isEmpty()) {
				finalList = unionedList;
			} else {
				finalList = k.intersection(finalList, unionedList);
			}

		}
		
		System.out.println("The unranked list is: " + finalList);
		System.out.println();
		
		ArrayList<Integer> rankedList = k.rankDocuments(keywords, finalList, t, i);
		Collections.reverse(rankedList);
		System.out.println();
		System.out.println("The ranked list is currently: " + rankedList);
		System.out.println();

		
		System.out.println("Search Results: Titles of related articles located below.");
		System.out.println();
		for (int n = 0; n <= rankedList.size() - 1; n++) {
			int docNum = rankedList.get(n);
			String articleTitle = i.articleTitleIndexToID.get(docNum);

			System.out.println(articleTitle);
		}
	}
}


class sortByScore implements Comparator<Pair<Integer, Double>>  { 
	@Override
	public int compare(Pair<Integer, Double> p1, Pair<Integer, Double> p2) {
		// TODO Auto-generated method stub
		Double x = p1.getSecond();
		Double y = p2.getSecond();
		if (x < y) {
			return -1;
		} else if (x > y) {
			return 1;
		} else {
			return 0;
		}
	}
}
