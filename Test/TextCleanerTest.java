import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextCleanerTest {
    private TextCleaner cleaner;

    @Before
    public void setup() {
        cleaner = new TextCleaner();
    }

    @Test
    public void testLowercasing() {
        String result = cleaner.clean("KebaB");
        assertEquals("kebab", result);
    }

    @Test
    public void testNonAlpha() {
        String result = cleaner.clean("the-world.");
        assertEquals("theworld", result);
    }

    @Test
    public void testStopwords() {
        String result = cleaner.clean("the");
        assertEquals(result, "");

        result = cleaner.clean("which");
        assertEquals("", result);
    }
}
