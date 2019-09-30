import java.util.Comparator;
import java.util.function.Consumer;

/**
 * A simplified weighted linked list class. When items are added, they are compared with existing
 * items and those with the highest comparison score are at the front.
 *
 * @author Michael S. Kirkpatrick
 * @version V1, 8/2018
 * @param <E>
 *          generic object type to be put into the list
 */
class WeightedList<E> {
  private Link<E> head;
  private Link<E> tail;
  private int size;

  /**
   * Constructor for a new weighted list.
   */
  WeightedList() {
    head = new Link<E>(null, null);
    tail = new Link<E>(head, null);
    head.setNext(tail);
    size = 0;
  }

  /**
   * Removes all of the elements from this list.
   */
  public void clear() {
    head.setNext(tail);
    size = 0;
  }

  /**
   * Ensures that this list contains the specified element.
   *
   * @param it
   *          The item to add to the list.
   * @param comp
   *          The method for comparing two items.
   *
   * @return Returns true if the item was successfully added.
   */
  public boolean add(E it, Comparator<E> comp) {
    boolean added = false;
    if (size == 0) {
      Link<E> newLink = new Link<E>(it, head, tail);
      head.setNext(newLink);
      tail.setPrev(newLink);
      size++;
      added = true;
    } else {
      Link<E> curr = head;
      while (!added && curr.next() != tail) {
        curr = curr.next();
        if (comp.compare(it, curr.element()) < 0) {
          Link<E> newLink = new Link<E>(it, curr.prev(), curr);
          curr.prev().setNext(newLink);
          curr.setPrev(newLink);
          size++;
          added = true;
        }
      }
      if (!added) {
        Link<E> newLink = new Link<E>(it, curr, tail);
        curr.setNext(newLink);
        tail.setPrev(newLink);
        size++;
        added = true;
      }
    }
    return added;
  }

  /**
   * Apply a Consumer lambda to each element in the list.
   *
   * @param cons
   *          A consumer lambda that will be applied to each element.
   */
  public void accept(Consumer<E> cons) {
    Link<E> curr = head;
    while (curr.next() != tail) {
      curr = curr.next();
      cons.accept(curr.element());
    }
  }

  /**
   * Get the first element of the list.
   *
   * @return The element with the highest weighting.
   */
  public E retrieve() {
    return head.next().element();
  }

  /**
   * Declares whether the list is empty.
   *
   * @return Returns true if this list contains no elements.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Gets the number of elements in the list.
   *
   * @return Returns the number of elements in the list.
   */
  public int size() {
    return size;
  }
}
