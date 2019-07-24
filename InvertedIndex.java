//package InvertedIndex;

import java.util.*;

import java.io.*;
import java.util.List;
//import java.util.zip.ZipInputStream;
import java.util.ArrayList; 


//import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.StartElement;
//import javax.xml.stream.events.XMLEvent;

public class InvertedIndex {
/*	private static final String ELEMENT_ARTICLE = "PubmedArticle";
	private static final String ELEMENT_TITLE = "ArticleTitle";
	private static final String ELEMENT_ID = "PMID";   
*/

	//private final static String file;  // = pubmed19n0023.xml;
	private final XMLInputFactory factory = XMLInputFactory.newInstance();
	

	public Hashtable <String, List< Pair<Integer, Integer> > > KeyWordsTable = new Hashtable <String, List <Pair<Integer, Integer> > >();
	
	public static final List<String> stopwords = Arrays.asList("a", "about", "above", "after", "again", "against", "ain", "all", "am", "an",
			"and", "any", "are", "aren", "aren't", "as", "at", "be", "because", "been",
			"before", "being", "below", "between", "both", "but", "by", "can", "couldn",
			"couldn't", "d", "did", "didn", "didn't", "do", "does", "doesn", "doesn't",
			"doing", "don", "don't", "down", "during", "each", "few", "for", "from",
			"further", "had", "hadn", "hadn't", "has", "hasn", "hasn't", "have",
			"haven", "haven't", "having", "he", "her", "here", "hers", "herself",
			"him", "himself", "his", "how", "i", "if", "in", "into", "is", "isn",
			"isn't", "it", "it's", "its", "itself", "just", "ll", "m", "ma", "me",
			"mightn", "mightn't", "more", "most", "mustn", "mustn't", "my", "myself",
			"needn", "needn't", "no", "nor", "not", "now", "o", "of", "off", "on",
			"once", "only", "or", "other", "our", "ours", "ourselves", "out", "over",
			"own", "re", "s", "same", "shan", "shan't", "she", "she's", "should",
			"should've", "shouldn", "shouldn't", "so", "some", "such", "t", "than",
			"that", "that'll", "the", "their", "theirs", "them", "themselves", "then",
			"there", "these", "they", "this", "those", "through", "to", "too", "under",
			"until", "up", "ve", "very", "was", "wasn", "wasn't", "we", "were", "weren",
			"weren't", "what", "when", "where", "which", "while", "who", "whom", "why",
			"will", "with", "won", "won't", "wouldn", "wouldn't", "y", "you", "you'd",
			"you'll", "you're", "you've", "your", "yours", "yourself", "yourselves",
			"could", "he'd", "he'll", "he's", "here's", "how's", "i'd", "i'll", "i'm",
			"i've", "let's", "ought", "she'd", "she'll", "that's", "there's", "they'd",
			"they'll", "they're", "they've", "we'd", "we'll", "we're", "we've",
			"what's", "when's", "where's", "who's", "why's", "would");

	/*public InvertedIndex(final String file, final ArrayList<Integer> articles) {
		this.file = file;
		this.articles = articles;
	} */
	public InvertedIndex() {
		
	}
	public void parse() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("pubmed19n0013.txt"))) {
		    String line;
		    int ID = 0;
		    ArrayList<String> articleTitleIndexToID = new ArrayList<String>();
		    System.out.println("Inside parse, and before while loop");
		    while ((line = br.readLine()) != null) {
		       createIndex(line, ID);
		       articleTitleIndexToID.add(line);
		       ID++;
		    }
		}
	}
/*
	public void parse() throws IOException, XMLStreamException {
		try(final InputStream stream = this.getClass().getResourceAsStream("pubmed19n0023.xml")) {
			try(final ZipInputStream zip = new ZipInputStream(stream)) {
				final XMLEventReader reader = factory.createXMLEventReader(zip);
				while (reader.hasNext()) {
					final XMLEvent event = reader.nextEvent();
					if (event.isStartElement() && event.asStartElement().getName()
							.getLocalPart().equals(ELEMENT_ARTICLE)) {
						parseArticle(reader);
					}
				}
			} catch (XMLStreamException e) {
				System.err.println("XMLStreamException");
			}
			
		} catch (IOException e) {
			System.err.println("IOException");

		}
	}

	// This function reads in an article from the XML zip and parses through the Titles and Id's
	private void parseArticle(final XMLEventReader reader) throws XMLStreamException {
		System.out.println("Parsing Articles");
		String name = null;
		String id = null;
		Integer indexID = 0;
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(ELEMENT_ARTICLE)) {
				return;
			}
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final String elementName = element.getName().getLocalPart();
				switch (elementName) {
				case ELEMENT_TITLE:
					name = reader.getElementText();
					break;
				case ELEMENT_ID:
					id = reader.getElementText();
					indexID++;
					break;
				}
			}
		}
		//final ArrayList<ArrayList<Integer>> articles = new ArrayList(ArrayList[id, indexID]);
		//articles.add(article);
		createIndex(name, indexID);
	}
	*/

	/* This function takes the title element from a Pubmed Article and breaks it into keywords.
       If the keyword is a stop word, then it is skipped.
       If the keyword is not in the hashtable yet, then a new arraylist is created for that word,
       and the keyword is put into the hashtable.
       The indexID is then appended to an array for that keyword called docArray
	 */
	public void createIndex(String titleElement, int ID){
		String [] keywords = titleElement.split("\\W+");
		for (String word : keywords) {
			word = word.toLowerCase();

			//check for stopwords, if current word is a stopword, it is skipped and not added to the index.
			if (stopwords.contains(word)) {
				continue;
			} 
			// case where the keyword is found in the hash table. If the word is already in the document, increase count and return
			if (KeyWordsTable.containsKey(word)) {
				List<Pair <Integer, Integer> >  keyToDocList = KeyWordsTable.get(word);
				int length = keyToDocList.size();
				int freq = 0;
				for (int i = 0; i < length; i++) {
					Pair<Integer,Integer> aPair = keyToDocList.get(i);
					int pairDocID = aPair.getFirst(); 
					if (pairDocID == ID){
						freq = aPair.getSecond();
                        freq++;       
                        Pair <Integer, Integer> returnPair = Pair.createPair(pairDocID, freq);
                        keyToDocList.set(i, returnPair);
						break;
					} 
				}
				if (freq == 0) {
					keyToDocList.add(new Pair<Integer, Integer> (ID, 1));
				}
				// append to array
				// writeback to table
			} else {
				//System.out.println("creating fresh table value");
				List<Pair<Integer,Integer>> docList = new ArrayList<>(); // array list for creating keeping track of document ID and # times word appears in doc
				docList.add(new Pair<Integer, Integer> (ID, 1));
				KeyWordsTable.put(word, docList);
			}
		}
	}

	public void find(String word) {
		
		if (KeyWordsTable.containsKey(word)) {
			System.out.println("The word " + word + " is in the documents: "); // for debugging
			List<Pair <Integer, Integer> > docs = KeyWordsTable.get(word);
		     for (int idx = 0; idx <= docs.size() - 1; idx++) {
		        System.out.print("(" + docs.get(idx).getFirst() + ", " + docs.get(idx).getSecond() + "), ");
		     }
		     System.out.println();
		}
	}
	
	public void printTable() {
		String str;
		Set<String> keys = KeyWordsTable.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext()) {
			 str = itr.next();
		     System.out.println("Key: "+str+" & Value: ");
		     List<Pair <Integer, Integer> > docs = KeyWordsTable.get(str);
		     for (int idx = 0; idx <= docs.size() - 1; idx++) {
		        System.out.print("(" + docs.get(idx).getFirst() + ", " + docs.get(idx).getSecond() + ")");
		     }
		     System.out.println();
		}
	}


	public static void main(String[] args) throws IOException, XMLStreamException {
		
		//String file = null;
		InvertedIndex i = new InvertedIndex();
		
		i.parse();
		
		String s; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Search the Medline Database: "); 
		s = sc.nextLine();  
		i.find(s);
		//i.printTable();
		sc.close();

	}

}

class Pair<K, V> {

	private final K element0;
	private final V element1;

	public static <K, V> Pair<K, V> createPair(K element0, V element1) {
		return new Pair<K, V>(element0, element1);
	}

	public Pair(K element0, V element1) {
		this.element0 = element0;
		this.element1 = element1;
	}

	public K getFirst() {
		return element0;
	}

	public V getSecond() {
		return element1;
	}

}