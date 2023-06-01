import java.io.IOException;



/**
 * The Crawler implements web crawling by starting from a link
 * and then indexing those pages. It limits itself to a host pattern
 * expression, and only crawls to a specified depth
 */
public class Crawler {
    /**
     * Create a web crawler that saves the inverted index to the given
     * filename
     *
     * @param filename The file to save the inverted index to
     */
    public Crawler(String filename) {
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
        return null;
    }

    /**
     * Save the inverted index to disk. The filename to save to was
     * given in the constructor
     */
    public void saveInvertedIndex() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Crawling...");
        Crawler crawler = new Crawler("inverted_index.ser");
        crawler.visit("https://wwu.edu", ".*wwu.edu", 3);
        crawler.saveInvertedIndex();
    }
}
