public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {

    protected BSTNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    @Override
    public void insert(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Cannot insert null values");
        }
        BSTNode<T> newNode = new BSTNode<>(data);
        if (root == null) {
            root = newNode;
        } else {
            insertHelper(newNode, root);
        }
    }

    protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
        if (newNode.data.compareTo(subtree.data) < 0) {
            if (subtree.left == null) {
                subtree.left = newNode;
                newNode.up = subtree;
            } else {
                insertHelper(newNode, subtree.left);
            }
        } else {  // Insert duplicates to the right subtree
            if (subtree.right == null) {
                subtree.right = newNode;
                newNode.up = subtree;
            } else {
                insertHelper(newNode, subtree.right);
            }
        }
    }



    @Override
    public boolean contains(Comparable<T> data) {
        return containsHelper(data, root);
    }

    private boolean containsHelper(Comparable<T> data, BSTNode<T> subtree) {
        if (subtree == null) {
            return false;
        }
        if (data.compareTo(subtree.data) == 0) {
            return true;
        } else if (data.compareTo(subtree.data) < 0) {
            return containsHelper(data, subtree.left);
        } else {
            return containsHelper(data, subtree.right);
        }
    }

    @Override
    public int size() {
        return sizeHelper(root);
    }

    private int sizeHelper(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeHelper(node.left) + sizeHelper(node.right);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void clear() {
        this.root = null;
    }
    
 // Test methods

    // Test 1: Inserting multiple values and checking tree structure
    public boolean test1() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);

        // Check if tree contains values as expected
        return bst.contains(10) && bst.contains(5) && bst.contains(15) && bst.contains(3) && bst.contains(7);
    }

    // Test 2: Finding values in different parts of the tree and checking tree structure for Strings
    public boolean test2() {
        BinarySearchTree<String> bst = new BinarySearchTree<>();
        bst.insert("apple");
        bst.insert("banana");
        bst.insert("cherry");
        bst.insert("date");
        bst.insert("fig");

        // Check if tree contains values and behaves as expected
        return bst.contains("apple") && bst.contains("banana") && bst.contains("cherry") && !bst.contains("grape");
    }

    // Test 3: Testing size and clear method functionality
    public boolean test3() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(1);
        bst.insert(2);
        bst.insert(3);
        bst.insert(4);

        // Check size before and after clearing
        if (bst.size() != 4) {
            return false;
        }
        bst.clear();
        return bst.isEmpty();
    }

    // Main method to run tests
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        // Run each test and print result
        System.out.println("Test 1 (Insert and Contains Integers): " + tree.test1());
        System.out.println("Test 2 (Insert and Contains Strings): " + tree.test2());
        System.out.println("Test 3 (Size and Clear Integers): " + tree.test3());
    }
    
    
}
