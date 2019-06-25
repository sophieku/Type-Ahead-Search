import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class InvertedIndex {
    List<String> stopwords = Arrays.asList("a", "about", "above", "after", "again", "against", "ain", "all", "am", "an",
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
    
    Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
    List<String> files = new ArrayList<String>();
 
    public void indexFile(File file) throws IOException {
	int fileno = files.indexOf(file.getPath());
	if (fileno == -1) {
	    files.add(file.getPath());
	    fileno = files.size() - 1;
	}
        
        int pos = 0;
	BufferedReader reader = new BufferedReader(new FileReader(file));
	for (String line = reader.readLine(); line != null; line = reader
		 .readLine()) {
	    // argh need to complete
	}
    }



    // fill in later
    public void search(List<String> words) {
	for (String _word : words) {
	}
    }

    public static void main(String[] args) {
	try { 
	    //do later
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private class Tuple {
	private int fileno;
	private int position;
 
	public Tuple(int fileno, int position) {
	    this.fileno = fileno;
	    this.position = position;
	}
    }
}
      
