import java.util.ArrayList;

public class Lab8P2 {
	public static class BTNode {
		int data;
		BTNode left;
		BTNode right;
		// Add constructor and/or other methods if required
	}

	public static int find_idx(int[] arr, int val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == val) return i;
		}
		return -1;
	}

	public static BTNode reconstruct_tree(int[] inOrder, int[] preOrder) {
		if (inOrder.length == 0) return null;

		BTNode root = new BTNode();
		root.data = preOrder[0];

		int partitionIdx = find_idx(inOrder, root.data);

		// Stuff to the left of partitionIdx must be the left subtree
		int[] leftInOrder = new int[partitionIdx];
		System.arraycopy(inOrder, 0, leftInOrder, 0, leftInOrder.length);
		int[] rightInOrder = new int[inOrder.length-partitionIdx-1];
		System.arraycopy(inOrder, leftInOrder.length+1, rightInOrder, 0, rightInOrder.length);

		int[] leftPreOrder = new int[leftInOrder.length];
		System.arraycopy(preOrder, 1, leftPreOrder, 0, leftPreOrder.length);
		// Pre-order length minus stuff on the left minus the root
		int[] rightPreOrder = new int[preOrder.length-leftPreOrder.length-1];
		System.arraycopy(preOrder, leftPreOrder.length+1, rightPreOrder, 0, rightPreOrder.length);

		root.left = reconstruct_tree(leftInOrder, leftPreOrder);
		root.right = reconstruct_tree(rightInOrder, rightPreOrder);
		return root;
	}

	public static void level_order_print(BTNode root) {
		ArrayList<BTNode> plevel = new ArrayList<BTNode>();

		plevel.add(root);
		while (!plevel.isEmpty()) {
			ArrayList<BTNode> level = new ArrayList<BTNode>();

			for (BTNode node: plevel) {
				System.out.print(node.data);
				System.out.print(' ');
				if (node.left != null) level.add(node.left);
				if (node.right != null) level.add(node.right);
			}

			plevel = level;

			System.out.println();
		}
	}

	/*
	 * Test is using this tree:
	 * 
	 *        1
	 *       / \
	 *      /   \
	 *     2     3
	 *    / \   / \
	 *   4   5 6   7
	 */
	public static void main(String[] args) {
		int[] inOrder = {4, 2, 5, 1, 6, 3, 7};
		int[] preOrder = {1, 2, 4, 5, 3, 6, 7};
		BTNode n = reconstruct_tree(inOrder, preOrder);
		level_order_print(n);
	}

}
