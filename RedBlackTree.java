import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class implements the Red-Black Tree insertion algorithm by extending
 * the BSTRotation class. It also includes JUnit test methods to verify
 * the correctness of the insertion and red property enforcement.
 */
public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {

    /**
     * Overrides the insert method to ensure nodes are inserted according to
     * Red-Black Tree properties.
     * @param data the new value to be inserted into the tree
     * @throws NullPointerException if data is null
     */
    @Override
    public void insert(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Cannot insert null values");
        }

        // Insert the new node using BinarySearchTree's insertHelper
        RBTNode<T> newNode = new RBTNode<>(data); // Ensure the new node is red by default
        if (root == null) {
            root = newNode; // Insert root as black
        } else {
            insertHelper(newNode, root);
        }

        // Call ensureRedProperty to check and repair any Red-Black violations
        ensureRedProperty(newNode);

        // Ensure the root is always black
        ((RBTNode<T>) this.root).isRed = false;
    }

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
        // Loop until the newRedNode is either the root or its parent is black
        while (newRedNode != root && ((RBTNode<T>) newRedNode.getUp()).isRed) {
            RBTNode<T> parent = (RBTNode<T>) newRedNode.getUp();       // Parent of the new node
            RBTNode<T> grandparent = (RBTNode<T>) parent.getUp();      // Grandparent of the new node

            // Case A: Parent is a left child of the grandparent
            if (parent == grandparent.getLeft()) {
                RBTNode<T> uncle = (RBTNode<T>) grandparent.getRight(); // Uncle node

                // Case 1: Uncle is red (recoloring case)
                if (uncle != null && uncle.isRed()) {
                    // Recolor parent and uncle to black, grandparent to red
                    parent.isRed = false;
                    uncle.isRed = false;
                    grandparent.isRed = true;

                    // Move the violation upwards to the grandparent
                    newRedNode = grandparent;
                } else {
                    // Case 2: Uncle is black or null (rotation case)
                    // Case 2a: New node is a right child, perform left rotation
                    if (newRedNode == parent.getRight()) {
                        rotate(newRedNode, parent);  // Left rotate
                        newRedNode = parent;         // Move newRedNode up
                        parent = (RBTNode<T>) newRedNode.getUp(); // Reassign parent
                    }

                    // Case 2b: New node is a left child, perform right rotation
                    parent.isRed = false;
                    grandparent.isRed = true;
                    rotate(parent, grandparent);     // Right rotate
                }
            } 
            // Case B: Parent is a right child of the grandparent (mirror of Case A)
            else {
                RBTNode<T> uncle = (RBTNode<T>) grandparent.getLeft(); // Uncle node

                // Case 1: Uncle is red (recoloring case)
                if (uncle != null && uncle.isRed()) {
                    // Recolor parent and uncle to black, grandparent to red
                    parent.isRed = false;
                    uncle.isRed = false;
                    grandparent.isRed = true;

                    // Move the violation upwards to the grandparent
                    newRedNode = grandparent;
                } else {
                    // Case 2: Uncle is black or null (rotation case)
                    // Case 2a: New node is a left child, perform right rotation
                    if (newRedNode == parent.getLeft()) {
                        rotate(newRedNode, parent);  // Right rotate
                        newRedNode = parent;         // Move newRedNode up
                        parent = (RBTNode<T>) newRedNode.getUp(); // Reassign parent
                    }

                    // Case 2b: New node is a right child, perform left rotation
                    parent.isRed = false;
                    grandparent.isRed = true;
                    rotate(parent, grandparent);     // Left rotate
                }
            }
        }

        // Ensure the root is always black
        ((RBTNode<T>) this.root).isRed = false;
    }

    // ================= JUnit Test Methods ====================

    /**
     * Test case for inserting into an empty Red-Black Tree.
     * This test ensures that the first inserted node is the root and is black.
     */
    @Test
    public void testInsertIntoEmptyTree() {
        // Create a new Red-Black Tree
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        // Insert a single value
        tree.insert(10);

        // Check that the root is correctly set and is black
        assertEquals(10, tree.root.getData()); // Ensure root contains correct data
        assertFalse(((RBTNode<Integer>) tree.root).isRed); // Root must be black
    }

    /**
     * Test case for inserting nodes that require recoloring.
     * This example is based on the quiz from Q03.RBTInsert, where recoloring happens.
     * This test checks that after inserting 3 nodes, recoloring occurs without rotation.
     */
    @Test
    public void testRecoloringCase() {
        // Create a new Red-Black Tree
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        // Insert nodes in such a way that recoloring happens
        tree.insert(10);
        tree.insert(5);
        tree.insert(15); // This should trigger recoloring (no rotation)

        // Check the colors of nodes after insertion
        assertFalse(((RBTNode<Integer>) tree.root).isRed); // Root should still be black
        assertTrue(((RBTNode<Integer>) tree.root.getLeft()).isRed); // Left child should be red
        assertTrue(((RBTNode<Integer>) tree.root.getRight()).isRed); // Right child should be red
    }

    /**
     * Test case for inserting nodes that require rotation to fix the Red-Black Tree.
     * This example tests the insertion that causes a left rotation followed by a right rotation.
     */
    @Test
    public void testRotationCase() {
        // Create a new Red-Black Tree
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        // Insert nodes in such a way that a rotation is required
        tree.insert(10);  // Root node
        tree.insert(5);   // Left child
        tree.insert(1);   // Inserting 1 should cause a right rotation on 5

        // Check if rotations fixed the tree correctly
        assertEquals(5, tree.root.getData()); // Root should now be 5 after rotation
        assertEquals(1, tree.root.getLeft().getData()); // Left child should be 1
        assertEquals(10, tree.root.getRight().getData()); // Right child should be 10
    }
}
