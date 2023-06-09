import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.MalformedURLException;


/**
 * The Crawler implements web crawling by starting from a link
 * and then indexing those pages. It limits itself to a host pattern
 * expression, and only crawls to a specified depth
 */
public class Crawler {
    String filename;
    ArrayList<Page> visitedPages; // string arraylist of pages with base urls
    InvertedIndex index;

    /**
     * Create a web crawler that saves the inverted index to the given
     * filename
     *
     * @param filename The file to save the inverted index to
     */
    public Crawler(String filename) {
        this.filename = filename;
        visitedPages = new ArrayList<>();
        index = new InvertedIndex();
    }

    /**
     * Visit and index the page at the link given. Recursively index the pages
     * given by links on the page up to a given depth from the starting
     * point. Return an inverted index that was created by the indexing.
     *
     * @param link The link to start from.
     * @param hostPattern The java.util.regex.Pattern to limit indexing to
     * @param depth How many levels to follow links
     * @return An inverted index formed by indexing the pages
     */
    public InvertedIndex visit(String link, String hostPattern, int depth) {
        // visit pseudocode:
        // 1) Check if the page was visited before
            // never visited before
            // visited before
        // 2) Index the page
            // get words
            // clean words
            // add words and page to InvertedIndex
        // 3) Decrement the depth
            // get links
            // if link matches host pattern
                // recursively visit each link
            // if link does not match host pattern
                // nothing



        /*if(depth < 0){
            return index;
        }*/

        //System.out.println("pagesVisited duplicates? " + Crawler.containsDuplicates(visitedPages));

        // 1)
        System.out.println("[" + depth +  "] Visiting " + link);

        String bLink = PageDigest.toBaseUrl(link); // base link
        Page newPage;

        if(visitedPages.size() != 0){ // if arraylist of visited URLs is not empty
            for(int i = 0; i < visitedPages.size(); i++){
                String currentLink = visitedPages.get(i).getLink();

                if(bLink.equals(currentLink)){ // bLink has been visited
                    newPage = new Page(bLink); // page from bLink
                    visitedPages.set(i, newPage);
                    index.increaseEveryRankOf(newPage);
                    return index;
                }
            }
        }


        // bLink has not been visited
        newPage = new Page(bLink);
        visitedPages.add(newPage);

        // 2)
        TextCleaner t = new TextCleaner();
        PageDigest d = Crawler.pageDigestOf(link); // PageDigest for link

        if(d == null){
            return index;
        }

        String[] words = d.getWords(); // array of all words at the link
        String[] wordsC = t.clean(words); // cleaned words
        index.add(wordsC, newPage); // add words and page to inverted index

        // 3)
        depth--;
        if(depth >= 0){
            ArrayList<String> links = d.getLinks(); // all links on current page

            // current link
            for (String l : links) {
                String hostName = Crawler.hostnameOf(l); // current hostname of link

                if(Pattern.matches(hostPattern, hostName)) { // if hostName matches passed hostPattern
                    visit(l, hostPattern, depth);
                }
            }
        }

        return index;
    }
    /**
     * Save the inverted index to disk. The filename to save to was
     * given in the constructor
     */
    public void saveInvertedIndex() throws IOException {
        FileOutputStream fs = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fs);
        out.writeObject(index);
        out.close();
        fs.close();
    }
    public static PageDigest pageDigestOf(String link){
        PageDigest pd;
        try {
            pd = new PageDigest(link);
        }catch(Exception e){
            //System.out.println("io error");
            pd = null;
        }

        return pd;
    }

    public static String hostnameOf(String link){
        try{
            URL url = new URL(link);
            return url.getHost(); // gets hostname of url
        }catch(MalformedURLException e){
            return null;
        }
    }

    public static boolean containsDuplicates(ArrayList<Page> pageAL){ // returns true if pageArrayList has duplicate pages
        for(int i = 0; i < pageAL.size(); i++) {
            Page currentPage = pageAL.get(i); // ith page
            int occurrencesOfPage = Collections.frequency(pageAL, currentPage);
            if(occurrencesOfPage > 1){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) throws IOException {
        /*System.out.println("\nCrawling...");
        Crawler crawler = new Crawler("inverted_index.ser");
        crawler.visit("https://wwu.edu", ".*wwu.edu", 1);
        crawler.saveInvertedIndex();*/

        /*// Testing containsDuplicates
        Page a = new Page(PageDigest.toBaseUrl("https://google.com"));
        Page b = new Page(PageDigest.toBaseUrl("https://lichess.org"));
        Page c = new Page(PageDigest.toBaseUrl("https://www.youtube.org"));
        Page d = new Page(PageDigest.toBaseUrl("https://www.chess.com"));

        ArrayList<Page> testList = new ArrayList<>();
        testList.add(a);
        testList.add(b);
        testList.add(c);

        System.out.println(Crawler.containsDuplicates(testList));*/

    }

}
