package lab9;

public class BSTree {
	BSTNode root;

	public boolean insert(int key) {
		if (root == null) {
			root = new BSTNode(key);
			return true;
		}

		return root.insert(key);
	}

	public boolean search(int key) {
		BSTNode result = root.search_subtree(key);
		if (result == null) return false;
		if (result.key != key) return false;
		return true;
	}

	public boolean remove(int key) {
		if (root == null) return false;
		if (root.key == key) {
			// XXX DRY this up
			if (root.left == null && root.right == null) {
				root = null;
			} else if (root.left != null && root.right != null) {
				BSTNode pre = root.predecessor();
				root.key = pre.key;
				if (pre.right != null) {
					pre.parent.left = null;
				} else {
					pre.parent.left = pre.right;
				}
			} else if (root.left != null) {
				root = root.left;
				root.parent = null;
			} else {
				root = root.right;
				root.parent = null;
			}
			return true;
		}
		return root.remove(key);
	}
}
