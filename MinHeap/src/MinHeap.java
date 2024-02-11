import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Yueqiao Chen
 * @version 1.0
 * @userid ychen3221
 * @GTID 903531127
 *
 * Collaborators: N/A
 *
 * Resources: canvas, lectures, piazza
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Can not use null arraylist to construct heap");
        } else {
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            size = data.size();
            for (int i = size; i > 0; i--) {
                backingArray[i] = data.remove(i - 1);
                if (backingArray[i] == null) {
                    throw new java.lang.IllegalArgumentException("Can not add null data when constructing heap");
                }
            }
            buildHeap(backingArray[size / 2], size / 2);
        }
    }

    private void buildHeap(T data, int index) {
        if (index == 0) {
            return;
        } else {
            buildHelper(data, index);
            buildHeap(backingArray[index - 1], index - 1);
        }
    }

    private void buildHelper(T data, int index) {
        if (index > size / 2) {
            return;
        } else {
            int indexOfChild = 2 * index;
            if (backingArray[index * 2 + 1] != null
                    && backingArray[index * 2 + 1].compareTo(backingArray[index * 2]) < 0) {
                indexOfChild = 2 * index + 1;
            }
            if (backingArray[index].compareTo(backingArray[indexOfChild]) > 0) {
                backingArray[index] = backingArray[indexOfChild];
                backingArray[indexOfChild] = data;
                buildHelper(data, indexOfChild);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Can not add null data");
        } else {
            if (size == backingArray.length - 1) {
                T[] newArray = (T[]) new Comparable[2 * backingArray.length];
                for (int i = 1; i <= size; i++) {
                    newArray[i] = backingArray[i];
                }
                backingArray = newArray;
            }
            backingArray[size + 1] = data;
            size++;
            addHelper(data, size);
        }
    }

    private void addHelper(T data, int index) {
        if (index == 1) {
            return;
        } else if (backingArray[index / 2].compareTo(data) > 0) {
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = data;
            addHelper(backingArray[index / 2], index / 2);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Can not remove from empty heap");
        } else {
            T temp = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            removeHelper(backingArray[1], 1);
            return temp;
        }
    }

    private void removeHelper(T data, int index) {
        if (index * 2 > size && backingArray[index * 2] == null) {
            return;
        } else {
            int indexOfChild = 2 * index;
            if (2 * index + 1 < size && backingArray[index * 2 + 1] != null
                    && backingArray[index * 2 + 1].compareTo(backingArray[index * 2]) < 0) {
                indexOfChild = 2 * index + 1;
            }
            if (backingArray[index].compareTo(backingArray[indexOfChild]) > 0) {
                backingArray[index] = backingArray[indexOfChild];
                backingArray[indexOfChild] = data;
                removeHelper(backingArray[indexOfChild], indexOfChild);
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Can not get minimum element in empty heap");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
