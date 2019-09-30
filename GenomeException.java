/**
 * Exception class for invalid parameters for either the GenomeOracle or the SequenceAnalyzer.
 *
 * @author Michael S. Kirkpatrick
 * @version V1, 8/2018
 */
public class GenomeException extends RuntimeException {

  /**
   * A static final serialVersionUID field of type long.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Create a run-time exception with a custom string.
   */
  public GenomeException() {
    super("Invalid base pair index");
  }
}
