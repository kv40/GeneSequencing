import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/*
 * This work complies with the JMU Honor Code.
 * References and Acknowledgments: TA Jeremy helped me with the 
 * longestSharedSequence method.
 */
/**
 * Sequence analyzer for up to two people at a time. If only one person is passed with a
 * constructor, then the only valid method is findCandidates. The longestSharedSequence and match
 * methods should both throw a GenomeException.
 *
 * @author Michael S. Kirkpatrick
 * @version V1, 8/2018
 */
public class SequenceAnalyzer implements Iterable<Region> {

  private Person person1;
  private Person person2;
  private String[] longestSeq;
  private String matches;
  private String dna;

  /**
   * Create an instance to analyze two people's gene sequences.
   *
   * @param first
   *          The first person in the list of two.
   * @param second
   *          The second person in the list of two.
   */
  public SequenceAnalyzer(Person first, Person second) {
    person1 = first;
    person2 = second;
  }

  /**
   * Create an instance to analyze a single person's gene sequences.
   *
   * @param person
   *          The only person to analyze.
   * 
   */
  public SequenceAnalyzer(Person person) {
    person1 = person;
    person2 = null;

  }

  /**
   * Find longest nucleotide sequence. This sequence can appear anywhere in the gene snippet, in
   * either a junk or coding region.
   *
   * @param start
   *          The index of the first nucleotide to start looking.
   * @param end
   *          The index of the last nucleotide to look.
   *
   * @return An array of all common substrings in the regions.
   */
  public String[] longestSharedSequence(long start, long end) {
    if (person2 == null) {
      throw new GenomeException();
    }
    longestSeq = new String[0];
    matches = "";
    String p1Code;
    String p2Code;
    Long index = start;
    while (index + 10000000 < end) {
      p1Code = person1.getSnippet(index, 10000000);
      p2Code = person2.getSnippet(index, 10000000);
      longestSharedSequenceHelper(p1Code, p2Code);
      index += 10000000;
    }
    p1Code = person1.getSnippet(index, (int) (end - index));
    p2Code = person2.getSnippet(index, (int) (end - index));
    longestSharedSequenceHelper(p1Code, p2Code);
    checkMatches();
    return longestSeq;
  }

  /**
   * A helper method that loops through each person's DNA to find shared chars.
   * 
   * @param c1
   *          The first person's DNA code segment
   * @param c2
   *          The second person's DNA code segment
   */
  private void longestSharedSequenceHelper(String c1, String c2) {
    int index = 0;
    while (index < c1.length()) {
      if (c1.charAt(index) == c2.charAt(index)) {
        matches += c1.charAt(index);
      } else {
        if (!(matches.equals(""))) {
          checkMatches();
        } else {
          matches = "";
        }
      }
      index++;
    }
  }

  /**
   * A method that applies the appropriate operation to a sequence of chars.
   */
  private void checkMatches() {
    if (longestSeq.length == 0 || longestSeq[0].length() < matches.length()) {
      longestSeq = new String[1];
      longestSeq[0] = matches;
    } else if (longestSeq[0].length() == matches.length()) {
      String[] temp = new String[longestSeq.length + 1];
      for (int j = 0; j < longestSeq.length; j++) {
        temp[j] = longestSeq[j];
      }
      temp[temp.length - 1] = matches;
      longestSeq = new String[temp.length];
      for (int j = 0; j < temp.length; j++) {
        longestSeq[j] = temp[j];
      }
    }
    matches = "";
  }

  /**
   * Find matched regions that satisfy some particular criterion. These criteria should compare
   * pairwise regions from two people and test to see if they match (such as having a certain number
   * of common nucleotides). The matched regions are returned in a priority order,
   *
   * @param start
   *          The index of the first nucleotide to start looking.
   * @param end
   *          The index of the last nucleotide to look.
   * @param condition
   *          A binary predicate that all regions must match.
   *
   * @return An array of all common substrings in the regions.
   */
  public WeightedList<Pair<Region, Region>> match(long start, long end,
      BiPredicate<Region, Region> condition) {
    if (person2 == null) {
      throw new GenomeException();
    }
    WeightedList<Pair<Region, Region>> matchedRegions = new WeightedList<Pair<Region, Region>>();
    Comparator<Pair<Region, Region>> priorityOrder = (p, r) -> {
      int lengthP1 = p.getFirst().getEncoding().length();
      int lengthR = r.getFirst().getEncoding().length();
      if (lengthP1 != lengthR) {
        return lengthR - lengthP1;
      } else {
        return (int) (p.getFirst().getLocation() - r.getFirst().getLocation());
      }
    };

    longestSeq = new String[0];
    Region tempP1;
    Region tempP2;
    long index = 0;
    dna = person1.getSnippet(start, 115);
    String dna2 = person2.getSnippet(index, 60);
    dna = person1.getSnippet(index, 60);
    Iterator<Region> iterP1 = iterator();
    dna2 = person2.getSnippet(index, 60);
    Iterator<Region> iterP2 = iterator();
    while (iterP1.hasNext()) {
      tempP1 = iterP1.next();
      tempP1.setLocation(index + tempP1.getLocation());
      dna = dna2;
      tempP2 = iterP2.next();
      tempP2.setLocation(index + tempP2.getLocation());
      index += 60;
      if (condition.test(tempP1, tempP2)) {
        Pair<Region, Region> matchedPair = new Pair<Region, Region>(tempP1, tempP2);
        matchedRegions.add(matchedPair, priorityOrder);
      }
      if (index + 60 > end) {
        dna = person1.getSnippet(index, (int) (end - index));
        dna2 = person2.getSnippet(index, (int) (end - index));
      } else {
        dna = person1.getSnippet(index, 60);
        dna2 = person2.getSnippet(index, 60);
      }
    }
    return matchedRegions;
  }

  /**
   * Find candidate gene coding regions that satisfy some criterion. Example criteria include having
   * a particular (or minimum) coding length, containing some codon sequence, etc.
   * 
   * @param start
   *          The index of the first nucleotide to start looking.
   * @param end
   *          The index of the last nucleotide to look.
   * @param first
   *          Determines if we are analyzing the first or second person.
   * @param condition
   *          The condition each region must follow to be added to the WeightedList.
   * @return A WeightedList containing all gene coding regions that follow the condition.
   */
  public WeightedList<Region> findCandidates(long start, long end, boolean first,
      Predicate<Region> condition) {
    Comparator<Region> comp = (r1, r2) -> {
      int lengthP1 = r1.getEncoding().length();
      int lengthR = r2.getEncoding().length();
      if (lengthP1 != lengthR) {
        return lengthR - lengthP1;
      } else {
        return (int) (r1.getLocation() - r2.getLocation());
      }
    };
    WeightedList<Region> candidates = new WeightedList<Region>();
    Person person;
    if (first) {
      person = person1;
    } else {
      person = person2;
    }
    Region temp;
    long index = 0;
    dna = person.getSnippet(start, 115);
    if (dna.indexOf("AUG") > dna.indexOf("UAG")) {
      index = dna.indexOf("UAG") + 3;
    }
    dna = person.getSnippet(index, 60);
    Iterator<Region> iter = iterator();
    while (iter.hasNext()) {
      temp = iter.next();
      temp.setLocation(index + temp.getLocation());
      index += 60;
      if (condition.test(temp)) {
        candidates.add(temp, comp);
      }
      if (index + 60 > end) {
        dna = person.getSnippet(index, (int) (end - index));
      } else {
        dna = person.getSnippet(index, 60);
      }
    }
    return candidates;
  }

  @Override
  public Iterator<Region> iterator() {
    return new SaIterator();
  }

  /**
   * This is my iterator class to iterate through DNA and find regions.
   * @author Kyle Vinsand
   *
   */
  private class SaIterator implements Iterator<Region> {
    Region currentRegion;
    int beginRegion;
    int endRegion;

    /**
     * Checks if the next region is in bounds.
     * @return if UAG is found in the given size
     */
    public boolean hasNext() {
      return (60 == dna.length());
    }

    /**
     * Returns the next region in the dna.
     * @return the next region
     */
    public Region next() {
      beginRegion = dna.indexOf("AUG") + 3;
      endRegion = dna.indexOf("UAG");
      String regionString = dna.substring(beginRegion, endRegion);
      currentRegion = new Region(regionString, (long) beginRegion);
      return currentRegion;
    }
  }

}
