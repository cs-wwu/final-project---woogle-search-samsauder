import java.io.Serializable;
import java.util.*;

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

    /*public Page get(int j){
        Iterator<Page> it = this.iterator(); // new iterator
        int jindex = 0;

        while(it.hasNext()){
            if(jindex != j){
                it.next();
                jindex++;
            }else{
                break;
            }
        }

        return it.next();
    }*/

    /**
     * Return the number of elements in the set
     */
    public int size() {
        return set.size();
    }

    public int rankOf(Page page){
        Iterator<Page> it = this.iterator();
        while(it.hasNext()){
            if(it.next().equals(page)){
                return page.getRank();
            }
        }
        return 0; // return 0 if page not found
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
    public PageSet intersectAll(PageSet[] others){ // Find the intersection with a collection of PageSets
        PageSet firstPageSet = this;
        PageSet interPageSet = new PageSet(); // provisional PageSet

        for(PageSet other : others){
            if(interPageSet.size() == 0){
                interPageSet = firstPageSet.intersect(other);
            }else{
                interPageSet = interPageSet.intersect(other);
            }
        }
        return interPageSet;
    }

    public static void main(String[] args){
        // Test intersect(PageSet[])
        Page a = new Page(PageDigest.toBaseUrl("https://google.com"));
        Page b = new Page(PageDigest.toBaseUrl("https://lichess.org"));
        Page c = new Page(PageDigest.toBaseUrl("https://www.youtube.org"));
        Page d = new Page(PageDigest.toBaseUrl("https://www.chess.com"));


        PageSet ps1 = new PageSet(); // test PageSet #1

        ps1.add(a);
        ps1.add(b);
        ps1.add(c);
        ps1.add(d);

        System.out.println("rank of a: " + ps1.rankOf(a));

        //System.out.println("here: " + ps1.get(1).toString());

        PageSet ps2 = new PageSet(); // test PageSet #2
        ps2.add(a);
        ps2.add(b);
        ps2.add(c);

        PageSet ps3 = new PageSet(); // test PageSet #3
        ps3.add(a);
        ps3.add(b);
        ps3.add(d);


        PageSet[] pageSets = {ps2, ps3}; // new array of PageSets

        PageSet ps4 = ps1.intersectAll(pageSets);

        Iterator<Page> ps4Iterator = ps4.iterator();

        /*System.out.println("Pages in common");
        while(ps4Iterator.hasNext()){
            System.out.println(ps4Iterator.next());
        }*/

    }
}
