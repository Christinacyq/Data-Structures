/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Yueqiao Chen
 * @version 1.0
 * @userid ychen3221
 * @GTID 903531127
 *
 * Collaborators: N/A
 *
 * Resources: canvas, lecture
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Can not add if index < 0 or index > size");
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("can not add null in doubly linked list");
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = newNode;
                tail = newNode;
            } else {
                if (index == 0) {
                    newNode.setNext(head);
                    head.setPrevious(newNode);
                    head = newNode;
                } else if (index == size) {
                    tail.setNext(newNode);
                    newNode.setPrevious(tail);
                    tail = newNode;
                } else {
                    DoublyLinkedListNode<T> curr;
                    if (index < size / 2) {
                        curr = head;
                        for (int i = 0; i < index - 1; i++) {
                            curr = curr.getNext();
                        }
                    } else {
                        curr = tail;
                        for (int i = size; i > index; i--) {
                            curr = curr.getPrevious();
                        }
                    }
                    newNode.setPrevious(curr);
                    newNode.setNext(curr.getNext());
                    curr.getNext().setPrevious(newNode);
                    curr.setNext(newNode);
                }
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("can not add null in doubly linked list");
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            }
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("can not add null in doubly linked list");
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = newNode;
            } else {
                tail.setNext(newNode);
                newNode.setPrevious(tail);
            }
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("can not remove data if index < 0 or index >= size");
        } else {
            T temp;
            if (size == 1) {
                temp = head.getData();
                head = null;
                tail = null;
            } else if (index == 0) {
                temp = head.getData();
                head = head.getNext();
                head.setPrevious(null);
            } else if (index == size - 1) {
                temp = tail.getData();
                tail = tail.getPrevious();
                tail.setNext(null);
            } else {
                DoublyLinkedListNode<T> curr;
                if (index < size / 2) {
                    curr = head;
                    for (int i = 0; i < index - 1; i++) {
                        curr = curr.getNext();
                    }
                } else {
                    curr = tail;
                    for (int i = size; i > index; i--) {
                        curr = curr.getPrevious();
                    }
                }
                temp = curr.getNext().getData();
                curr.setNext(curr.getNext().getNext());
                curr.getNext().setPrevious(curr);
            }
            size--;
            return temp;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("can not remove from an empty list");
        } else {
            T temp = head.getData();
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.getNext();
                head.setPrevious(null);
            }
            size--;
            return temp;
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("can not remove from an empty list");
        } else {
            T temp = tail.getData();
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                tail = tail.getPrevious();
                tail.setNext(null);
            }
            size--;
            return temp;
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("can not get data if index < 0 or index >= size");
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("can not remove null data");
        } else if (size == 0) {
            throw new java.util.NoSuchElementException("data does not exist in the list");
        } else if (size == 1) {
            if (head.getData().equals(data)) {
                T temp = head.getData();
                head = null;
                tail = null;
                size--;
                return temp;
            } else {
                throw new java.util.NoSuchElementException("data does not exist in the list");
            }
        } else {
            DoublyLinkedListNode<T> curr = tail;
            T temp = null;
            if (tail.getData().equals(data)) {
                temp = tail.getData();
                tail = tail.getPrevious();
                tail.setNext(null);
                size--;
                return temp;
            } else {
                while (curr.getPrevious() != null) {
                    if (curr.getPrevious().getData().equals(data)) {
                        temp = curr.getPrevious().getData();
                        break;
                    }
                    curr = curr.getPrevious();
                }
                if (temp == null) {
                    throw new java.util.NoSuchElementException("data does not exist in the list");
                } else {
                    curr.setPrevious(curr.getPrevious().getPrevious());
                    curr.getPrevious().setNext(curr);
                    size--;
                    return temp;
                }
            }
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        if (size != 0) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size; i++) {
                array[i] = curr.getData();
                curr = curr.getNext();
            }
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
