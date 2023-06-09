import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Main {
    public static void main(String[] args){
        // Testing
        Search s;
        String SERIALIZATIONFILE = "crawl_test.ser";
        String LINK = "https://wwu.edu";
        String HOSTPATTERN = ".*wwu.edu";

        /*
        // testLookupSingleWord()
        Search s = new Search(LINK, HOSTPATTERN, 0);
        String[] queryWords = new String[] { "about" };
        Page[] set = s.search(queryWords);
        assertEquals(1, set.length);

        // testLookupMultiWordsDeeper()
        Search search = new Search(LINK, HOSTPATTERN, 1);
        String[] queryWords = new String[] { "research", "funding" };
        Page[] result = search.search(queryWords);
        assertTrue(result.length > 1);
        assertTrue(contains(result, "gradschool"));
        assertTrue(contains(result, "foundation"));*/
        String[] queryWords = {"research"};
        s = new Search(LINK, HOSTPATTERN, 1); // new Search
        Page[] searchResults = s.search(queryWords);
        System.out.println(Arrays.toString(searchResults));
    }
}
