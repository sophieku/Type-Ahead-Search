package Trie;
import java.util.ArrayList;

public class Trie { 

	public Trie() {
		root = new TrieNode();
	}

	// Alphabet size (# of symbols) 
	static final int ALPHABET_SIZE = 36; //alphabet and numbers


	static class TrieNode 
	{ 
		TrieNode[] children = new TrieNode[ALPHABET_SIZE]; 

		// isEndOfWord is true if the node represents 
		// end of a word 
		boolean isEndOfWord; 
		int rng_start, rng_end;
		String completedWord = "";
		

		TrieNode(){ 
			isEndOfWord = false; 
			for (int i = 0; i < ALPHABET_SIZE; i++) 
				children[i] = null; 
		} 
	}; 

	TrieNode root; 
	ArrayList<String> kwArray;

	// If not present, inserts key into trie 
	// If the key is prefix of trie node, 
	// just marks leaf node 
	public void insert(String key) 
	{ 
		int level; 
		int length = key.length(); 
		int index; 

		TrieNode pCrawl = root; 

		for (level = 0; level < length; level++) 
		{ 
			if (key.charAt(level) >= '0' && key.charAt(level) <= '9' ) {
				index = key.charAt(level) - '0';
			} else if (key.charAt(level) >= 'a' && key.charAt(level) <= 'z') {
				index = key.charAt(level) - 'a' + 10; 
			} else {
				continue;
			}

			if (pCrawl.children[index] == null) { 
				pCrawl.children[index] = new TrieNode();
			}

			pCrawl = pCrawl.children[index]; 
		} 

		// mark last node as leaf 
		pCrawl.completedWord = key;
		
		
	} 

	// Returns true if key presents in trie, else false 
	public boolean search(String key) 
	{ 
		int level; 
		int length = key.length(); 
		int index; 
		TrieNode pCrawl = root; 

		for (level = 0; level < length; level++) 
		{ 
			if (key.charAt(level) >= '0' && key.charAt(level) <= '9' ) {
				index = key.charAt(level) - '0';
			} else if (key.charAt(level) >= 'a' && key.charAt(level) <= 'z') {
				index = key.charAt(level) - 'a' + 10; 
			} else {
				continue;
			}

			if (pCrawl.children[index] == null) 
				return false; 

			pCrawl = pCrawl.children[index]; 
		} 
		
		// Printing kwArray values for testing
		/*System.out.println("Start index: " + pCrawl.rng_start);
		System.out.println("End index: " + pCrawl.rng_end);
		System.out.println("Completed Words: ");
		for (int i = pCrawl.rng_start; i < pCrawl.rng_end; i++) {
			System.out.print(", " + kwArray.get(i));
		}
		*/
		
		return (pCrawl != null && pCrawl.completedWord != ""); 
	} 

	public static void main() {

	}


	public void CreateKeywordArray() {
		kwArray = new ArrayList<String>(); // kwarray is a variable of this class
		TraverseTrieAndCreateArray(root);
	}

	public void TraverseTrieAndCreateArray(TrieNode n) {
		n.rng_start = kwArray.size(); // initialize the starting position of n's range in the array
		n.rng_end = kwArray.size() - 1;  // initialize the ending position of n's range in the array

		if (n.completedWord != "") {
			kwArray.add(n.completedWord); // insert a keyword to kwarray
			n.rng_end++;
		}

		for (int i = 0; i < 36; i++) {
			TrieNode c = n.children[i];
			if (c != null) {
				TraverseTrieAndCreateArray(c);
				n.rng_end = c.rng_end;
			}
		}
	}
	
	public ArrayList<String> findCompletedWordsInTrie(String key) {
		
		ArrayList<String> returnList = new ArrayList<String>();
		returnList.add(key);
		int level; 
		int length = key.length(); 
		int index; 
		TrieNode pCrawl = root; 

		for (level = 0; level < length; level++) 
		{ 
			if (key.charAt(level) >= '0' && key.charAt(level) <= '9' ) {
				index = key.charAt(level) - '0';
			} else if (key.charAt(level) >= 'a' && key.charAt(level) <= 'z') {
				index = key.charAt(level) - 'a' + 10; 
			} else {
				continue;
			}

			if (pCrawl.children[index] == null) 
				return returnList; 

			pCrawl = pCrawl.children[index]; 
		} 
		
		
		for (int i = pCrawl.rng_start; i < pCrawl.rng_end; i++) {
			 returnList.add(kwArray.get(i));
		}
		return returnList; 
	}
}
