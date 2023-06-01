import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import java.util.HashSet;

public class InvertedIndexTest {
    private InvertedIndex invertedIndex;
    private String GIANT = "http://giant";
    private String GAME = "http://game";

    @Before
    public void setup() {
        invertedIndex = new InvertedIndex();
    }

    @Test
    public void testAdd() {
        String[] words1 = new String[] { "fee", "fi", "fo", "fum" };
        Page page1 = new Page(GIANT);
        invertedIndex.add(words1, page1);

        String[] words2 = new String[] { "rock", "paper", "scissors", "fum" };
        Page page2 = new Page(GAME);
        invertedIndex.add(words2, page2);
    }

    @Test
    public void testLookupNotPresent() {
        testAdd();
        PageSet result = invertedIndex.lookup("llama");
        assertEquals(0, result.size());
    }

    @Test
    public void testLookupSinglePage() {
        testAdd();
        PageSet result = invertedIndex.lookup("rock");
        assertEquals(1, result.size());
        Iterator<Page> it = result.iterator();
        Page p = it.next();
        assertEquals(GAME, p.getLink());
    }

    @Test
    public void testLookupMultiPage() {
        testAdd();
        PageSet result = invertedIndex.lookup("fum");
        assertEquals(2, result.size());
        Iterator<Page> it = result.iterator();
        Page p = it.next();
        assertTrue(p.getLink().equals(GAME) || p.getLink().equals(GIANT));
        p = it.next();
        assertTrue(p.getLink().equals(GAME) || p.getLink().equals(GIANT));
    }
}
