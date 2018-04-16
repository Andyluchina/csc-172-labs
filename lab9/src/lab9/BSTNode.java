package lab9;

import java.lang.ref.Reference;
import java.util.function.Function;

public class BSTNode {
	int key;
	BSTNode parent;
	BSTNode left;
	BSTNode right;

	public BSTNode(int key) {
		this.key = key;
	}

	public void inorder_traverse(Function<Integer, ?> f) {
		if (left != null) left.inorder_traverse(f);
		f.apply(key);
		if (right != null) right.inorder_traverse(f);
	}

	public boolean insert(int key) {
		if (this.key == key) return false;
		if (key < this.key) {
			if (left == null ) {
				left = new BSTNode(key);
				left.parent = this;
				return true;
			} else {
				return left.insert(key);
			}
		}
		if (key > this.key) {
			if (right == null ) {
				right = new BSTNode(key);
				right.parent = this;
				return true;
			} else {
				return right.insert(key);
			}
		}
		return false; // Dead code; just here to shut the type-checker up
	}

	public BSTNode predecessor() {
		if (left != null) return left.rightmost();
		return this;
	}

	public BSTNode rightmost() {
		if (right != null) return right.rightmost();
		return this;
	}

	public boolean remove(int key) {
		// XXX handle root node since `parent` will cause NullPointerException
		BSTNode candidate = search_subtree(key);
		if (candidate == null) return false;
		assert candidate.key == key;
		if (candidate.parent.left == candidate) {
			// XXX DRY this up
			if (candidate.left == null && candidate.right == null) {
				candidate.parent.left = null;
			} else if (candidate.left != null && candidate.right != null) {
				BSTNode pre = candidate.predecessor();
				this.key = pre.key;
				if (pre.right != null) {
					pre.parent.left = null;
				} else {
					pre.parent.left = pre.right;
				}
			} else if (candidate.left != null) {
				candidate.parent.left = candidate.left;
				candidate.parent.left.parent = candidate.parent;
			} else {
				assert candidate.right != null;
				candidate.parent.left = candidate.right;
				candidate.parent.left.parent = candidate.parent;
			}
		} else {
			// Right
			// XXX DRY this up
			if (candidate.left == null && candidate.right == null) {
				parent.right = null;
			} else if (candidate.left != null && candidate.right != null) {
				BSTNode pre = candidate.predecessor();
				this.key = pre.key;
				if (pre.right != null) {
					pre.parent.right = null;
				} else {
					pre.parent.right = pre.right;
				}
			} else if (candidate.left != null) {
				candidate.parent.right = candidate.left;
				candidate.parent.right.parent = candidate.parent;
			} else {
				assert candidate.right != null;
				candidate.parent.right = candidate.right;
				candidate.parent.right.parent = candidate.parent;
			}
		}
		return true;
	}

	public BSTNode search_subtree(int key) {
		if (this.key == key) return this;
		if (this.left != null && key < this.key) return this.left.search_subtree(key);
		if (this.right != null && key > this.key) return this.right.search_subtree(key);
		return null;
	}
}
