
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class Search {
    /**
     * Create a Search object that reads from the given filename
     *
     * @param filename The name of the file to read the saved inverted index
     */
    InvertedIndex ii; // current invertedIndex
    public Search(String filename) throws IOException, ClassNotFoundException {
        // reconstruct from file
        FileInputStream is = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(is);
        InvertedIndex index = (InvertedIndex) in.readObject(); // read inverted index
        ii = index; // update inverted index
    }

    /**
     * Create a search object that also crawls the web with the given
     * values. This function should create a Crawler, have the crawler create
     * an inverted index, then save the inverted index.
     *
     * @param link The link to start crawling from
     * @param hostPattern The pattern to limit host names for links
     * @param depth The number of levels to crawl
     */
    public Search(String link, String hostPattern, int depth) {
        // create a search object
        Crawler crawler = new Crawler("inverted_index.ser");

        ii = crawler.visit(link, hostPattern, depth); // crawl with inputs

        try {
            crawler.saveInvertedIndex();
            //System.out.println("saved");
        }catch(Exception e){ // catch ioexception

        }
    }


    /**
     * Search the inverted index for the given query words. The result
     * contains Pages where all words are found, sorted with the highest
     * rank first.
     *
     * @param queryWords An array of Strings that are the query words
     * @return An array of Pages that is the query result.
     */
    public Page[] search(String[] queryWords) {
        // Use TextCleaner to clean the user's query
        System.out.println("# of query words: " + queryWords.length);
        TextCleaner t = new TextCleaner(); // new TextCleaner
        String[] queryWordsC = t.clean(queryWords); // cleaned queryWords


        // Use InvertedIndex to look up the PageSets for each word
        System.out.println("\nSearch query " + Arrays.toString(queryWordsC));

        PageSet[] otherPageSets = new PageSet[queryWordsC.length - 1];

        PageSet firstPageSet = ii.lookup(queryWordsC[0]); // PageSet associated with the first query word

        for(int i = 1; i < queryWordsC.length; i++){ // for each query word
            PageSet currentPageSet = ii.lookup(queryWordsC[i]); // the PageSet associated with the ith query word
            otherPageSets[i - 1] = currentPageSet; // update otherPageSets
        }


        // Find the intersection of all the PageSets
        PageSet intersectPS;
        if(queryWordsC.length > 1){ // more than one query word
            intersectPS = firstPageSet.intersectAll(otherPageSets);
        }else{ // only one query word
            intersectPS = firstPageSet;
        }

        // Sort pages by rank from high to low
        Iterator<Page> it = intersectPS.iterator(); // new iterator
        List<Page> pagesL = new ArrayList<>(); // starting pages list

        while(it.hasNext()){
            pagesL.add(it.next());
        }

        Page[] pages = new Page[pagesL.size()];
        pagesL.sort(Collections.reverseOrder());


        // Print and return result
        pages = pagesL.toArray(pages); // newly sorted Pages[] array


        for(Page p : pages){
            System.out.println("        " + p.getLink() + ", rank=" + p.getRank());
        }

        return pages;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Search s = new Search("https://wwu.edu", ".*wwu.edu", 1);
//        String[] queryWords = {"research"};
//        //Page[] pageResults = s.search(queryWords);
//        Page[] pageResults = s.search(queryWords);
//        //System.out.println(pageResults);
//        //System.out.println("\nResults: " + Arrays.toString(pageResults));

        Search search;
        String SERIALIZATIONFILE = "crawl_test.ser";
        String LINK = "https://wwu.edu";
        String HOSTPATTERN = ".*wwu.edu";

        Search s = new Search(LINK, HOSTPATTERN, 2);
        String[] queryWords = new String[] { "about" };
        //String[] queryWords = new String[] { "research", "funding" };

        Page[] result = s.search(queryWords);
        //System.out.println(Arrays.toString(result));


        //assertTrue(result.length > 1);
        //assertTrue(contains(result, "gradschool"));
        //assertTrue(contains(result, "foundation"));

    }
}
