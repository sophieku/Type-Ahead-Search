package invertedIndex;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.util.ArrayList; 

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class InvertedIndex {
    private static final String ELEMENT_ARTICLE = "PubmedArticle";
    private static final String ELEMENT_TITLE = "ArticleTitle";
    private static final String ELEMENT_ID = "PMID";

    private final String file;
    private final XMLInputFactory factory = XMLInputFactory.newInstance();
    private final List<PubmedArticles> articles;
    
    public final Hashtable <String, ArrayList<Integer>> KeywordsTable = new Hashtable <String, ArrayList<Integer>>();
   
    public InvertedIndex(final String file, final List<PubmedArticles> articles) {
        this.file = file;
        this.articles = articles;
    }

    public void parse() throws IOException, XMLStreamException {
        try(final InputStream stream = this.getClass().getResourceAsStream(file)) {
		try(final ZipInputStream zip = new ZipInputStream(stream)) {
			final XMLEventReader reader = factory.createXMLEventReader(zip);
			while (reader.hasNext()) {
			    final XMLEvent event = reader.nextEvent();
			    if (event.isStartElement() && event.asStartElement().getName()
				.getLocalPart().equals(ELEMENT_ARTICLE)) {
				parseArticle(reader);
			    }
			}
		    }
	    }
    }

    private void parseArticle(final XMLEventReader reader) throws XMLStreamException {
        String name = null;
        String id = null;
        String indexID = 0;
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
        final PubmedArticles article = new PubmedArticles(id, indexID);
        articles.add(article);
        createIndex(name, indexID);
    }

    /* This function takes the title element from a Pubmed Article and breaks it into keywords.
       If the keyword is a stop word, then it is skipped.
       If the keyword is not in the hashtable yet, then a new arraylist is created for that word,
       and the keyword is put into the hashtable.
       The indexID is then appended to an array for that keyword called docArray
    */
    public void createIndex(String titleElement, int ID){
        String [] keywords = element.split("\\W+");
        for (String word : keywords) {
            word = word.toLowerCase();
            //check for stopwords
            if (stopwords.contains(word)) {
	        continue;
            } 
           
            String docArray = word + "List";
            
            if (KeyWordsTable.containsKey(word)) {
                docArray.add(ID);
	    } else {
                ArrayList<Integer> docArray = new ArrayList<Integer>();
                docArray.add(ID);
                KeyWordsTable.put(word, docArray);
	    }
        }
    }
    public void find(String word) {
        String docArray = word + "List";
        if (KeyWordsTable.containsKey(word)) {
	    System.out.println("The word" + word + " is in the documents: " + docArray + ".");
	}
    }
}