package lab9;

public class BSTreeTest {

	public static void main(String[] args) {
		BSTree tree = new BSTree();
		int[] arr = {5, 18, 3, 25, 27, 45, 97, 88, 26, 15, 17, 16};
		for (int i: arr) tree.insert(i);
		tree.root.inorder_traverse(i -> {
			System.out.print(i);
			System.out.print(' ');
			// The typechecker made me
			return true;
		});
		System.out.println();
		int[] searches = {3, 88, 27, 28};
		for (int i: searches) System.out.println(tree.search(i));
		int[] dels = {88, 18, 5, 3};
		for (int i: dels) {
			System.out.println(tree.remove(i) ? "Item deleted" : "Item not found");
			tree.root.inorder_traverse(j -> {
				System.out.print(j);
				System.out.print(' ');
				// The typechecker made me
				return true;
			});
			System.out.println();
		}
		
	}

}
