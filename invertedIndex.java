import java.io.*;
import java.util.*;
package invertindex;

class Index {
    Map<Integer,String> sources;
    HashMap<String, HashSet<Integer>> index;

    Index(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
    }
    public void buildIndex(String[] files){
        int i = 0;
        for(String fileName:files){


            try(BufferedReader file = new BufferedReader(new FileReader(fileName)))
                   {
                       sources.put(i,fileName);
                       String ln;
                       while( (ln = file.readLine()) !=null) {
                           String[] words = ln.split("\\W+");
                           for(String word:words){
                               word = word.toLowerCase();
                               if (!index.containsKey(word))
                                   index.put(word, new HashSet<Integer>());
                               index.get(word).add(i);
                           }
                       }
                   } catch (IOException e){
                System.out.println("File "+fileName+" not found. Skip it");
            }
	    i++;
        }

    }
    public void find(String phrase){
        String[] words = phrase.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for(String word: words){
            res.retainAll(index.get(word));
        }

        if(res.size()==0) {
            System.out.println("Not found");
            return;
        }
        System.out.println("Found in: ");
        for(int num : res){
            System.out.println("\t"+sources.get(num));
        }

    }
}
public class InvertedIndexTest {


    public static void main(String args[]) throws IOException{
        Index index = new Index();
        index.buildIndex(new String[]{"pubmed19n0002.xml", "pubmed19n0013.xml", "pubmed19n0023.xml"});

        System.out.println("Print search phrase: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String phrase = in.readLine();

        index.find(phrase);

    }
}
