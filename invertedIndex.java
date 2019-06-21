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
	    // modify this later, imcomplete
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
      
