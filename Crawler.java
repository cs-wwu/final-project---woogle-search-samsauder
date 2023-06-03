import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


/**
 * The Crawler implements web crawling by starting from a link
 * and then indexing those pages. It limits itself to a host pattern
 * expression, and only crawls to a specified depth
 */
public class Crawler {
  String filename;
  ArrayList<Page> visitedLinks;
  InvertedIndex index;

    /**
     * Create a web crawler that saves the inverted index to the given
     * filename
     *
     * @param filename The file to save the inverted index to
     */
    public Crawler(String filename) {
      this.filename = filename;
      visitedLinks = new ArrayList();
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
      String baseLink = PageDigest.toBaseUrl(link); //Store base URL
      PageDigest digest;
      TextCleaner cleaner;
      //Check if page has been visited only if there are elements in visitedLinks
      if(visitedLinks.size() > 0) {

        //Check if the link given is already in VisitedLinks
        for(Page p : visitedLinks) {
          if(p.getLink() == baseLink) {
            p.increaseRank(); //Increase rank of page if link is found in list
            return index;
          }
        }
      }

      //If page has not been visited
      Page newPage = new Page(link); //Create a new page & add page to VisitedLinks
      visitedLinks.add(new Page(baseLink));

      try {
        //Get text from webpage and clean the text
        cleaner = new TextCleaner();
        digest = new PageDigest(newPage.getLink());
        String[] words = cleaner.clean(digest.getWords());

        //Add text and page to index
        index.add(words, newPage);
      }
      catch(Exception e) {
        return index;
      }


      //Decrement the depth and recursively loop

      if(depth >= 0) {
        ArrayList<String> links = digest.getLinks(); //Stores links found on URL
        for(String hostname : links) {

          //Check if hostname contains the hostPattern format
          if(Pattern.matches(hostPattern, hostname)) {
          return visit(hostname, hostPattern, depth - 1); //Loop
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

    }

    public static void main(String[] args) throws IOException {
        System.out.println("Crawling...");
        Crawler crawler = new Crawler("inverted_index.ser");
        crawler.visit("https://wwu.edu", ".*wwu.edu", 3);
        crawler.saveInvertedIndex();
    }
}
