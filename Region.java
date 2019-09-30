/*
 * This work complies with the JMU Honor Code.
 * References and Acknowledgments: TA Jeremy helped me with the 
 * longestSharedSequence method.
 */
/**
 * Representation of a gene coding region. At the least, this class should contain the sequence of
 * nucleotides between the start and stop codons, and the index where the sequence starts. You may
 * add other functionality to this class if it will be beneficial for your design.
 *
 * @author Michael S. Kirkpatrick
 * @version V1, 8/2018
 */
public class Region {

  private String encoding;
  private long location;

  /**
   * Creates a new Region to represent a gene coding region.
   *
   * @param encoding
   *          The string contents of the region.
   * @param location
   *          The index where the region starts.
   */
  public Region(String encoding, long location) {
    this.encoding = encoding;
    this.location = location;
  }

  /**
   * Gets the string of the gene coding region.
   *
   * @return The string of nucleotide codes.
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Gets the location of the region in the genome.
   *
   * @return A zero-based index where the region starts.
   */
  public long getLocation() {
    return location;
  }
  
  /**
   * Sets the location of the region in the genome.
   *@param location
   *          Where the region begins.
   *
   */
  public void setLocation(long location) {
    this.location = location;
  }

}
