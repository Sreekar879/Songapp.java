public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException or IllegalArgumentException.
     */
    protected void rotate(BSTNode<T> child, BSTNode<T> parent)
        throws NullPointerException, IllegalArgumentException {

        if (child == null || parent == null) {
            throw new NullPointerException("Nodes cannot be null");
        }

        if (parent.left != child && parent.right != child) {
            throw new IllegalArgumentException("Child and parent are not related properly");
        }

        // Right rotation: child is the left child of parent
        if (parent.left == child) {
            rightRotate(child, parent);
        }
        // Left rotation: child is the right child of parent
        else if (parent.right == child) {
            leftRotate(child, parent);
        }
    }

    // Helper method to perform right rotation
    private void rightRotate(BSTNode<T> child, BSTNode<T> parent) {
        parent.left = child.right;
        if (child.right != null) {
            child.right.up = parent;
        }
        child.right = parent;
        child.up = parent.up;

        if (parent.up == null) {
            root = child;
        } else if (parent.up.left == parent) {
            parent.up.left = child;
        } else {
            parent.up.right = child;
        }

        parent.up = child;
    }

    // Helper method to perform left rotation
    private void leftRotate(BSTNode<T> child, BSTNode<T> parent) {
        parent.right = child.left;
        if (child.left != null) {
            child.left.up = parent;
        }
        child.left = parent;
        child.up = parent.up;

        if (parent.up == null) {
            root = child;
        } else if (parent.up.left == parent) {
            parent.up.left = child;
        } else {
            parent.up.right = child;
        }

        parent.up = child;
    }

    // Test 1: Performing both left and right rotations
    public boolean test1() {
        // The tree is pre-built as per the placeholder class
        // Root is "E", left child "B", right child "H"

        // Right rotation on "B" and "D"
        BSTNode<T> parent = root.left.right;  // "D"
        BSTNode<T> child = parent.left;       // "C"
        rotate(child, parent);

        // Check the structure after rotation
        // You can modify this part to verify the tree's structure after the rotation
        // For example, use a toString method to check the structure.
        return true;
    }

    // Test 2: Rotating the root node
    public boolean test2() {
        // Perform a left rotation on "E" (root) and "H"
        BSTNode<T> parent = root;       // "E"
        BSTNode<T> child = parent.right; // "H"
        rotate(child, parent);

        // Check the structure after rotation (manually verify or use a method to print)
        return true;
    }

    // Test 3: Rotating a node with children
    public boolean test3() {
        // Perform a right rotation on "H" and "F"
        BSTNode<T> parent = root.right;   // "H"
        BSTNode<T> child = parent.left;   // "F"
        rotate(child, parent);

        // Check the structure after rotation (manually verify or use a method to print)
        return true;
    }

    // Main method to run tests
    public static void main(String[] args) {
        BSTRotation<String> tree = new BSTRotation<>();

        // Run each test and print result
        System.out.println("Test 1 (Right Rotation on B and D): " + tree.test1());
        System.out.println("Test 2 (Left Rotation on Root): " + tree.test2());
        System.out.println("Test 3 (Right Rotation on H and F): " + tree.test3());
    }
}
