package lab9;

public class HeapTest {

	public static void main(String[] args) {
		int[] arr = {5, 18, 3, 25, 27, 45, 97, 88, 26, 16, 49, 67};
		Heap.heapify(arr);
		for (int i: arr) {
			System.out.print(i);
			System.out.print(' ');
		}
		System.out.println();
		int[] arr2 = {15, 99, 3, 77, 27, 45, 7, 88, 26, 5};
		Heap.heapsort(arr2);
		for (int i: arr2) {
			System.out.print(i);
			System.out.print(' ');
		}
		System.out.println();
	}

}
