import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import java.util.HashSet;

public class PageSetTest {
    private PageSet set;
    private String PAGELINK = "http://123";

    @Before
    public void setup() {
        set = new PageSet();
    }

    @Test
    public void testAdd() {
        Page page = new Page(PAGELINK);
        set.add(page);
        assertEquals(1, set.size());
    }

    @Test
    public void testContains() {
        testAdd();

        Page p2 = new Page(PAGELINK);
        assertTrue(set.contains(p2));
    }

    @Test
    public void testIntersect() {
        PageSet set1 = new PageSet();
        Page p11 = new Page(PAGELINK + "1");
        Page p12 = new Page(PAGELINK + "2");
        Page p13 = new Page(PAGELINK + "3");
        Page p14 = new Page(PAGELINK + "4");
        set1.add(p11);
        set1.add(p12);
        set1.add(p13);
        set1.add(p14);

        PageSet set2 = new PageSet();
        Page p21 = new Page(PAGELINK + "123");
        Page p22 = new Page(PAGELINK + "2");
        Page p23 = new Page(PAGELINK + "3");
        Page p24 = new Page(PAGELINK + "456");
        set2.add(p21);
        set2.add(p22);
        set2.add(p23);
        set2.add(p24);

        PageSet intersect = set1.intersect(set2);
        assertEquals(2, intersect.size());
        assertTrue(intersect.contains(p22));
        assertTrue(intersect.contains(p23));
    }

    @Test
    public void testIterator() {
        PageSet set1 = new PageSet();
        Page p11 = new Page(PAGELINK + "1");
        Page p12 = new Page(PAGELINK + "2");
        Page p13 = new Page(PAGELINK + "3");
        Page p14 = new Page(PAGELINK + "4");
        set1.add(p11);
        set1.add(p12);
        set1.add(p13);
        set1.add(p14);

        Iterator<Page> it = set1.iterator();
        for (int i = 0; i < 4; i++) {
            assertTrue(it.hasNext());
            it.next();
        }
    }
}
