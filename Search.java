
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


public class Search {
    /**
     * Create a Search object that reads from the given filename
     *
     * @param filename The name of the file to read the saved inverted index
     */
    public Search(String filename) throws IOException, ClassNotFoundException {
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
        return new Page[0];
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Search");
        Search search = new Search("inverted_index.ser");
        String[] queryWords = new String[] { "research", "funding" };
        Page[] sorted = search.search(queryWords);
        for (Page p: sorted) {
            System.out.println(p);
        }

    }
}
