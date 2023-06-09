import java.io.Serializable;
import java.util.*;

/**
 * Implements an inverted index. This class maps a words to a PageSet.
 */
public class InvertedIndex implements Serializable {
  HashMap<String, PageSet> map;
    /**
     * Create an inverted index
     */
    public InvertedIndex() {
      map = new HashMap();
    }

    /**
     * Lookup a word in the index. This operation should be fast - O(1).
     *
     * @param word The word to look up
     * @return The PageSet (set of pages) that the word occurs in
     */
    public PageSet lookup(String word) {
        if(!map.containsKey(word)) {
          //System.out.println("\n    WORD NOT FOUND\n");
          return new PageSet();
        }
        return map.get(word);
    }

    /**
     * Add to the inverted index. The array of words are associated to the
     * given Page. The effect is that the Page is added to the PageSet for
     * each of the words.
     *
     * @param words The array of words that belong on the given Page
     * @param page The page that the words are on.
     */
    public void add(String[] words, Page page) {
      for(String word : words) {
        if(!map.containsKey(word)) {
          PageSet newEntry = new PageSet();
          newEntry.add(page);
          map.put(word, newEntry);
        }
        else {
          map.get(word).add(page);
        }
      }
    }

    /*public void increaseEveryRankOf(Page page){ // increases the rank of each page
      HashMap<String, PageSet> newMap = this.map;

      String[] words = newMap.keySet().toArray(new String[0]);

      for(String word : words){
        PageSet pageSet = this.lookup(word);

        if(pageSet.contains(page)){
          pageSet.set.remove(page);
          pageSet.add(new Page(page.getLink(), page.getRank() + 1));
        }

        newMap.put(word, pageSet);
      }
      this.map = newMap;
    }*/

    public void increaseEveryRankOf(Page page){
      String[] words = this.map.keySet().toArray(new String[0]); // array of all words (keys)
      List<PageSet> wordsPageSets = new ArrayList<>();

      // first get updated PageSets
      for(String word : words){
        PageSet wordPS = this.lookup(word); // PageSet associated with current word

        if(wordPS.contains(page)) { // replace matching page with a page with the same link and +1 rank
          wordPS.set.remove(page);
          // get rank of current page
          Page newPage = page;
          newPage.increaseRank();
          wordPS.set.add(newPage);
        }
        wordsPageSets.add(wordPS);
      }

      // then populate the map with the PageSets
      for(int w = 0; w < words.length; w++){
        String currentWord = words[w];
        PageSet currentPS = wordsPageSets.get(w);

        this.map.replace(currentWord, currentPS);
      }
    }

    /**
     * The toString function lets you print out an InvertedIndex in a
     * friendly way. This will help you debug.
     */
    public String toString() {
        return "";
    }
    public static void main(String[] args){
      Page a = new Page(PageDigest.toBaseUrl("https://google.com"));
      Page b = new Page(PageDigest.toBaseUrl("https://lichess.org"));
      Page c = new Page(PageDigest.toBaseUrl("https://www.youtube.org"));

      PageSet ps1 = new PageSet(); // test PageSet #1
      ps1.add(a);
      ps1.add(b);
      ps1.add(c);

      InvertedIndex ii = new InvertedIndex();
      ii.map.put("bean", ps1);
      ii.map.put("egg", ps1);
      ii.map.put("rat", ps1);

      System.out.println("\nrank of all pages in bean");

      //ii.increaseEveryRankOf(a);
      //ii.increaseEveryRankOf(a);
      //ii.increaseEveryRankOf(a);

      PageSet beanSet = ii.lookup("bean");
      Iterator<Page> beanIt = beanSet.iterator();

      while(beanIt.hasNext()){
        Page currentPage = beanIt.next();
        System.out.println(currentPage.getLink() + " [Rank: " + currentPage.getRank() + "]");
      }
    }
}
