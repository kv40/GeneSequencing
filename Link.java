/**
 * OpenDSA implementation of a singly linked list node.
 *
 * @author OpenDSA
 * @version V1, 8/2018
 * 
 * @param <E> The element being stored in the link
 */
class Link<E> {
  private E e; // Value for this node
  private Link<E> n; // Point to next node in list
  private Link<E> p; // Pointer to previous node

  /**
   * Create a new node that points to an existing node.
   *
   * @param it
   *          The value to store in this node.
   * @param inp
   *          The node to point to previously.
   * @param inn
   *          The node to point to next.
   */
  Link(E it, Link<E> inp, Link<E> inn) {
    e = it;
    p = inp;
    n = inn;
  }

  /**
   * Create a new node with no next node.
   *
   * @param inp
   *          The previous node.
   * @param inn
   *          The next node.
   */
  Link(Link<E> inp, Link<E> inn) {
    e = null;
    p = inp;
    n = inn;
  }

  /**
   * Getter for the node element.
   *
   * @return The node's current value.
   */
  E element() {
    return e;
  }

  /**
   * Setter for the node element.
   *
   * @param it
   *          The node's new value.
   * @return The node's updated value.
   */
  E setElement(E it) {
    this.e = it;
    return e;
  }

  /**
   * Getter for the node's next pointer.
   *
   * @return The node's current next pointer.
   */
  Link<E> next() {
    return n;
  }

  /**
   * Setter for the node's next pointer.
   *
   * @param inn
   *          The node's new next pointer.
   * @return The node's updated next pointer.
   */
  Link<E> setNext(Link<E> inn) {
    this.n = inn;
    return n;
  }

  /**
   * Getter for the node's previous pointer.
   *
   * @return The node's current previous pointer.
   */
  Link<E> prev() {
    return p;
  }

  /**
   * Setter for the node's previous pointer.
   *
   * @param inp
   *          The node's new previous pointer.
   * @return The node's updated previous pointer.
   */
  Link<E> setPrev(Link<E> inp) {
    this.p = inp;
    return p;
  }
}
