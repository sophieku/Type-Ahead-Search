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


	// function that takes in a term t and document d, and returns the score using tf*idf
	public double score(String t, Integer d, InvertedIndex i) {
		double tf = 0;
		double idf;

		List <Pair <Integer, Integer> > wordToDocList = i.KeyWordsTable.get(t);
		System.out.println("kwtable size: " + i.KeyWordsTable.size());

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
		for (int n = 0; n <= unrankedList.size(); n++) {
			Double docScore = 0.0;
			Integer docNumber = unrankedList.get(n);
			for (String word : keywords) {
				ArrayList<String> completedKeywordList = t.findCompletedWordsInTrie(word);
				for (int j = 0; j <= completedKeywordList.size() - 1; j++) {
					docScore = docScore + score(word, docNumber, i);
				}
			}
			Pair<Integer, Double> scoreNumPair  =  Pair.createPair(docNumber, docScore);
			docScoreList.add(scoreNumPair);
		}


		ArrayList<Integer> rankedList = new ArrayList<Integer>();
		return rankedList;

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

			System.out.println("The current keyword is: " + word);
			System.out.println("The completedKeywordList is: " + completedKeywordList);
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

		//System.out.println("score testing" + k.score("uterus", 19, i));

		System.out.println("Search Results: Titles of related articles located below.");
		for (int n = 0; n <= finalList.size() - 1; n++) {
			int docNum = finalList.get(n);
			String articleTitle = i.articleTitleIndexToID.get(docNum);

			System.out.println(articleTitle);
		}


	}
}

class Compare { 

	static void compare(ArrayList<Pair<Integer, Double>> arr, int n) 
	{ 
		// Comparator to sort the pair according to second element 
		Arrays.sort(arr, new Comparator<Pair<Integer, Double>>() { 
			@Override public int compare(Pair p1, Pair p2) 
			{ 
				return p1.y - p2.y; 
			} 
		}); 

	} 
 
}
