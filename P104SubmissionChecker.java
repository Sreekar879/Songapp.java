import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class extends the RedBlackTree class to run submission checks on it.
 */
public class P104SubmissionChecker extends RedBlackTree<Integer> {

    /**
     * Submission check that verifies if the root node of a newly created tree is correctly balanced.
     * It inserts three nodes into the tree and checks if the tree structure follows the Red-Black Tree rules.
     * 
     * This specific test inserts "a", "b", and "c" and checks that the tree root is "b",
     * with "a" as the left child and "c" as the right child, and ensures the correct colors.
     */
    @Test
    public void submissionCheckerSmallTree() {
        // Create a new Red-Black Tree with String values
        RedBlackTree<String> tree = new RedBlackTree<>();
        
        // Insert three nodes into the tree
        tree.insert("a");
        tree.insert("b");
        tree.insert("c");

        // Check if the tree structure is as expected
        Assertions.assertTrue(tree.root.toLevelOrderString().equals("[ b(b), a(r), c(r) ]"));
    }

    /**
     * Override for the ensureRedProperty method with the required signature.
     * This will cause compilation to fail if the method does not exist in RedBlackTree.
     * 
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    @Override
    protected void ensureRedProperty(RBTNode<Integer> newRedNode) {
        // No implementation needed, but ensures method exists for compilation checks
    }
}
