/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */

    private int solve(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return solve(node.left) + 1 + solve(node.right);
        }
    }

    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if (node == null) {
            return 0;
        } else if (child == Child.LEFT) {
            return solve(node.left);
        } else {
            return solve(node.right);
        }
    }

    /**
     * Build an array of nodes in the specified subtree.
     *
     * @param node the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */

    void enumerateChild(TreeNode[] result, TreeNode node, int prev) {
        if (node != null) {
            this.enumerateChild(result, node.left, prev);

            // position of curr node is the number of left child nodes it has
            int pos = this.countNodes(node, Child.LEFT) + prev;
            result[pos] = node;

            // position of right child node is the sum of its
            // left child nodes + parent node position + 1
            this.enumerateChild(result, node.right, pos+1);
        }
    }

    TreeNode[] enumerateNodes(TreeNode node, Child child) {
        int size = this.countNodes(node, child);
        TreeNode[] result = new TreeNode[size];

        if (child == Child.LEFT) {
            this.enumerateChild(result, node.left, 0);
        } else {
            this.enumerateChild(result, node.right, 0);
        }

        return result;
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        int size = nodeList.length;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return new TreeNode(nodeList[0].key);
        } else {
            int mid = size / 2;
            TreeNode result = new TreeNode(nodeList[mid].key);

            TreeNode[] leftArr = new TreeNode[mid];
            System.arraycopy(nodeList, 0, leftArr, 0, mid);
            result.left = buildTree(leftArr);

            TreeNode[] rightArr = new TreeNode[size-mid-1];
            System.arraycopy(nodeList, mid+1, rightArr, 0, size-mid-1);
            result.right = buildTree(rightArr);

            return result;
        }
    }

    /**
    * Rebuild the specified subtree of a node.
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Insert a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }

    public String printTree(TreeNode node) {
        if (node == null) {
            return "";
        } else {
            return printTree(node.left) + node.key +
                    printTree(node.right);
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();

        for (int i = 0; i < 9; i++) {
            tree.insert(i);
        }

        tree.rebuild(tree.root, Child.RIGHT);
    }
}
