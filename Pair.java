/**
 * Immutable 2-tuple type.
 * 
 * @author Nathan Sprague and Michael S. Kirkpatrick
 * @version V2, 1/2018
 * 
 * @param <T> The first pair object
 * @param <U> The second pair object
 */
public class Pair<T, U> {

  private final T first;
  private final U second;

  /**
   * Create an Pair with the provided objects.
   * 
   * @param first
   *          The first object.
   * @param second
   *          The second object.
   */
  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Gets the first object of the Pair.
   *
   * @return The first item of the Pair.
   */
  public T getFirst() {
    return first;
  }

  /**
   * Gets the second object of the Pair.
   *
   * @return The second item of the Pair.
   */
  public U getSecond() {
    return second;
  }

  /**
   * Returns the ordered Pair within angle brackets.
   *
   * @return String representation of the Pair.
   */
  @Override
  public String toString() {
    return "<" + first.toString() + ", " + second.toString() + ">";
  }

}
