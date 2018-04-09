import java.util.ArrayList;

public class Lab8P1 {
	public static class BTNode {
		int data;
		BTNode left;
		BTNode right;
		// Add constructor and/or other methods if required
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

	public static void main(String[] args) {
		// Construct a dummy tree
		BTNode root = new BTNode();
		root.data = 1;

		root.left = new BTNode();
		root.left.data = 2;
		root.right = new BTNode();
		root.right.data = 3;

		root.left.left = new BTNode();
		root.left.left.data = 4;
		root.left.right = new BTNode();
		root.left.right.data = 5;
		root.right.left = new BTNode();
		root.right.left.data = 6;
		root.right.right = new BTNode();
		root.right.right.data = 7;

		// And print.
		level_order_print(root);
	}

}
