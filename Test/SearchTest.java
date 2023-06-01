import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Iterator;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


public class SearchTest {
    private Search search;
    private String SERIALIZATIONFILE = "crawl_test.ser";
    private String LINK = "https://wwu.edu";
    private String HOSTPATTERN = ".*wwu.edu";

    @Before
    public void setup() {

    }

    @Test
    public void testReadSerialization() throws Exception {
        Crawler crawler = new Crawler(SERIALIZATIONFILE);
        crawler.visit(LINK, HOSTPATTERN, 0);
        crawler.saveInvertedIndex();

        search = new Search(SERIALIZATIONFILE);
    }

    @Test
    public void testLookupSingleWord() {
        Search search = new Search(LINK, HOSTPATTERN, 0);
        String[] queryWords = new String[] { "about" };
        Page[] set = search.search(queryWords);
        assertEquals(1, set.length);
    }

    @Test
    public void testLookupMultiWordsDeeper() {
        Search search = new Search(LINK, HOSTPATTERN, 1);
        String[] queryWords = new String[] { "research", "funding" };
        Page[] result = search.search(queryWords);
        assertTrue(result.length > 1);
        assertTrue(contains(result, "gradschool"));
        assertTrue(contains(result, "foundation"));
    }

    public static boolean contains(Page[] pages, String link) {
        for (Page p: pages) {
            if (p.getLink().contains(link)) {
                return true;
            }
        }
        return false;
    }

}
