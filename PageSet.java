import java.io.Serializable;
import java.util.Iterator;
import java.util.HashSet;

/*
 * A set of pages. It is used in the InvertedIndex.
 */
public class PageSet implements Serializable {

  HashSet<Page> set;
    /**
     * Create an empty page set
     */
    public PageSet() {
      this.set = new HashSet();
    }

    /**
     * Add a page to this page set if it does not already exist in the set.
     *
     * @param page The page to add
     * @return True if the page was added successfully, false otherwise.
     */
    public boolean add(Page page) {
        return set.add(page);
    }

    /**
     * Return true if this page set contains the current page. The set
     * contains the page if there is a page e in the set such
     * that e.equals(page)
     *
     * @param page The page to check
     * @return True if the set contains this page, or false otherwise
     */
    public boolean contains(Page page) {
        return set.contains(page);
    }

    /**
     * Return the number of elements in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * Return an iterator that allows you to iterate over all the pages in the
     * set
     */
    public Iterator<Page> iterator() {
        return set.iterator();
    }

    /**
     * Return the intersection of this set with the other set.
     * That is, return a new set with only the Pages that occur in both sets.
     *
     * @param other The other page set to intersect with
     * @return A page set that is the intersection of both sets
     */
    public PageSet intersect(PageSet other) {

        PageSet result = new PageSet();
        Iterator<Page> it = other.iterator();
        while(it.hasNext()) {
          Page currentPage = it.next();

          if(this.contains(currentPage)) {
            result.add(currentPage);
          }
        }
        return result;
    }
}
