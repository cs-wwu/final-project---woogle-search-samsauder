import java.io.Serializable;


/**
 * Implements an inverted index. This class maps a words to a PageSet.
 */
public class InvertedIndex implements Serializable {
    /**
     * Create an invertex index
     */
    public InvertedIndex() {
    }

    /**
     * Lookup a word in the index. This operation should be fast - O(1).
     *
     * @param word The word to look up
     * @return The PageSet (set of pages) that the word occurs in
     */
    public PageSet lookup(String word) {
        return null;
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
    }

    /**
     * The toString function lets you print out an InvertedIndex in a
     * friendly way. This will help you debug.
     */
    public String toString() {
        return "";
    }
}
