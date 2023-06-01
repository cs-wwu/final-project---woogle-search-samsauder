/**
 * This class cleans up text with a combination of lowercasing,
 * non-alpha removal, stop word removal and stemming.
 */
import java.io.*;
import java.util.*;

public class TextCleaner {
  ArrayList<String> stopwords;
    /**
     * Create a TextCleaner object
     */
    public TextCleaner() {
    }

    /**
     * Clean a word. Return the cleaned word, or "" if it was a stop word
     *
     * @param word The word to clean
     * @return The cleaned word
     */
    public String clean(String word) {
      //Set word to lowercase
        word = word.toLowerCase();

      //Check if word is  a stop word
      readStopWords();
      if(stopwords.contains(word)) {
        return "";
      }
        //Remove all non-alphabetic characters
        for(int i = 0; i < word.length(); i++) {
          char c = word.charAt(i);
          if(!Character.isAlphabetic(c)) {
            word = word.replace(Character.toString(c), "");
          }
        }

        //Get root word of the word
        PorterStemmer stemmer = new PorterStemmer();
        String result = stemmer.stem(word);
        return result;
    }

    public void readStopWords() {
      stopwords = new ArrayList<String>();
      try {
        FileInputStream fis = new FileInputStream("Lib/stopwords.txt");
        Scanner s = new Scanner(fis);
        while(s.hasNextLine()) {
          String word = s.nextLine();
          stopwords.add(word);
        }
      }
      catch(Exception e) {

      }
    }

    /**
     * Clean an array of words by applying clean to each word.
     *
     * @param words An array of words to clean
     * @return A new array of words which are all cleaned.
     */
    public String[] clean(String[] words) {
        for(String s : words) {
          s = clean(s);
        }
        return words;
    }
}
