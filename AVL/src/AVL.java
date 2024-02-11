import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Yueqiao Chen
 * @userid ychen3221
 * @GTID 903531127
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot build AVL with null collection");
        } else {
            for (T element: data) {
                if (element == null) {
                    throw new java.lang.IllegalArgumentException("cannot add null element in AVL");
                }
                add(element);
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("can not add null data to AVL");
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Helper method of add method.
     *
     * @param node the current node
     * @param data the data passed in
     * @return the root
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else {
            return node;
        }
        update(node);
        return balance(node);
    }

    /**
     * Balance the AVL during adding.
     *
     * @param node the current node.
     * @return the new sub root of subtree
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            node = rightRotate(node);
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            node = leftRotate(node);
        }
        update(node);
        return node;
    }

    /**
     * Perform left rotate.
     *
     * @param node the current node
     * @return the new sub root of subtree
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        node.setRight(node.getRight().getLeft());
        temp.setLeft(node);
        update(node);
        update(temp);
        return temp;
    }

    /**
     * Perform right rotate.
     *
     * @param node the current node
     * @return the new sub root of subtree
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        node.setLeft(node.getLeft().getRight());
        temp.setRight(node);
        update(node);
        update(temp);
        return temp;
    }

    /**
     * Update each node's height and balanced factor.
     *
     * @param node the current node
     */
    private void update(AVLNode<T> node) {
        if (node == null) {
            node.setHeight(-1);
        } else if (node.getLeft() == null && node.getRight() == null) {
            node.setHeight(0);
            node.setBalanceFactor(0);
        } else {
            node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
            node.setBalanceFactor(height(node.getLeft()) - height(node.getRight()));
        }
    }

    /**
     * Get the height of node, including null node.
     *
     * @param node the current node
     * @return the height of node
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot remove null data");
        } else if (root == null) {
            throw new java.util.NoSuchElementException("cannot remove for empty BST");
        } else {
            AVLNode<T> temp = new AVLNode<>(null);
            root = removeHelper(root, data, temp);
            return temp.getData();
        }
    }

    /**
     * Helper method of remove method.
     *
     * @param node the current node
     * @param data the data we want to remove
     * @param temp the return node of remove method
     * @return the root
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data, AVLNode<T> temp) {
        if (node == null) {
            throw new java.util.NoSuchElementException("cannot remove data if data is not in BST");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), data, temp));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(node.getRight(), data, temp));
        } else {
            temp.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null && node.getRight() != null) {
                return node.getRight();
            } else if (node.getRight() == null && node.getLeft() != null) {
                return node.getLeft();
            } else {
                AVLNode<T> successor = new AVLNode<>(null);
                node.setRight(successorFinder(node.getRight(), successor));
                node.setData(successor.getData());
            }
        }
        update(node);
        return balance(node);
    }

    /**
     * Helper method to find successor.
     *
     * @param node the data we want to find.
     * @param temp the return node of remove method.
     * @return the successor node
     */
    private AVLNode<T> successorFinder(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successorFinder(node.getLeft(), temp));
        }
        update(node);
        return balance(node);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot get data if data is null");
        } else if (root == null) {
            throw new java.util.NoSuchElementException("cannot get data in empty BST");
        } else {
            return getHelper(root, data).getData();
        }
    }

    /**
     * Helper method of get method.
     *
     * @param node the current node
     * @param data the data value we want to get
     * @return the node's data in the tree that equal to the data we want to find
     */
    private AVLNode<T> getHelper(AVLNode<T> node, T data) {
        if (node != null) {
            if (data.equals(node.getData())) {
                return node;
            } else if (data.compareTo(node.getData()) < 0) {
                return getHelper(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelper(node.getRight(), data);
            }
        }
        throw new java.util.NoSuchElementException("cannot get data if data is not in BST");
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("BST doesn't contain null data");
        } else if (root == null) {
            return false;
        } else {
            return containsHelper(root, data);
        }
    }

    /**
     * Helper method of contain method.
     *
     * @param node the current node
     * @param data the data we want to search
     * @return whether the AVL contain the data.
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node != null) {
            if (node.getData().equals(data)) {
                return true;
            } else if (data.compareTo(node.getData()) < 0) {
                return containsHelper(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return containsHelper(node.getRight(), data);
            }
        }
        return false;
    }
    //
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        return help(root);
    }

    private T help(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getData();
        }
        if (node.getBalanceFactor() > 0) {
            return help(node.getLeft());
        } else {
            return help(node.getRight());
        }
    }



    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        dBHelper(root, list);
        return list;
    }

    /**
     * Helper method of Deepest Branch method.
     *
     * @param node the current node
     * @param list the list we want to return in Deepest Branch method
     */
    private void dBHelper(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        } else {
            list.add(node.getData());
            if (node.getLeft() != null && node.getBalanceFactor() >= 0) {
                dBHelper(node.getLeft(), list);
            }
            if (node.getRight() != null && node.getBalanceFactor() <= 0) {
                dBHelper(node.getRight(), list);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new java.lang.IllegalArgumentException("can not sort null data in between");
        } else if (data1.compareTo(data2) > 0) {
            throw new java.lang.IllegalArgumentException("the lower bound is higher than upper bound");
        } else {
            List<T> list = new ArrayList<>();
            if (data1.equals(data2)) {
                return list;
            }
            sortHelper(root, list, data1, data2);
            return list;
        }
    }

    /**
     * Helper method of sort in between method.
     *
     * @param node the current node
     * @param list the list of data
     * @param data1 the lower bound of interval
     * @param data2 the upper bound of interval
     */
    private void sortHelper(AVLNode<T> node, List<T> list, T data1, T data2) {
        if (node == null) {
            return;
        } else {
            if (node.getData().compareTo(data1) <= 0) {
                sortHelper(node.getRight(), list, data1, data2);
            } else if (node.getData().compareTo(data2) >= 0) {
                sortHelper(node.getLeft(), list, data1, data2);
            } else {
                sortHelper(node.getLeft(), list, data1, data2);
                list.add(node.getData());
                sortHelper(node.getRight(), list, data1, data2);
            }
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}