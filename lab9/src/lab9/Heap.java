package lab9;

public class Heap {
	// Modified from lecture slides
	public static void r_sink(int[] arr, int i, int n) {
		int left = 2*i + 1;
		if (n > arr.length || left >= n) return;
		int right = left + 1;
		int my_pick = (right >= n) ? left :
			(arr[right] > arr[left]) ? right : left;
		if (arr[i] < arr[my_pick]) {
			int tmp = arr[i];
			arr[i] = arr[my_pick];
			arr[my_pick] = tmp;
			r_sink(arr, my_pick, n);
		}
	}

	// Taken from lecture slides
	public static void heapify(int[] arr) {
		for (int i = arr.length/2; i>=0; i--) r_sink(arr, i, arr.length);
	}

	// Modified from lecture slides
	public static void heapsort(int[] arr) {
		heapify(arr);
		for (int j=arr.length-1; j>=1; j--) {
			int tmp = arr[0];
			arr[0] = arr[j];
			arr[j] = tmp;
			r_sink(arr, 0, j);
		}
	}
}
