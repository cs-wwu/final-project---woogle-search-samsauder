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


public class CrawlTest {
    private Crawler crawler;
    private String SERIALIZATIONFILE = "crawl_test.ser";
    private String LINK = "http://wwu.edu";
    private String HOSTPATTERN = ".*wwu.edu";

    @Before
    public void setup() {
        crawler = new Crawler(SERIALIZATIONFILE);
    }

    @Test
    public void testLimitedCrawl() {
        InvertedIndex invertedIndex = crawler.visit(LINK, HOSTPATTERN, 0);
        assertTrue(invertedIndex != null);
        PageSet set = invertedIndex.lookup("about");
        assertEquals(1, set.size());
        Iterator<Page> it = set.iterator();
        Page p = it.next();
        assertEquals(LINK, p.getLink());
    }

    @Test
    public void testDeeperCrawl() {
        InvertedIndex invertedIndex = crawler.visit(LINK, HOSTPATTERN, 1);
        assertTrue(invertedIndex != null);
    }

    @Test
    public void testWriteSerialization() {
        try {
            Path path = Paths.get(SERIALIZATIONFILE);
            Files.deleteIfExists(path);
            crawler.saveInvertedIndex();
            assertTrue(Files.exists(path));
        } catch (Exception e) {
            fail();
        }
    }

    @After
    public void teardown() {
        try {
            Path path = Paths.get(SERIALIZATIONFILE);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            // do nothing
        }
    }
}
