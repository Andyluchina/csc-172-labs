/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program to play with various sorting algorithms. 
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Random;
import java.io.PrintWriter;

public class Sorting {

	public static final void swap(int[] arr, int a, int b) {
		int tmp = arr[b];
		arr[b] = arr[a];
		arr[a] = tmp;
	}

	// Source: slides
	public static void MergeSort(int[] arr) {
		if (arr.length <= 1) return;

		int[] left = new int[arr.length/2];
		int[] right = new int[arr.length - arr.length/2];
		System.arraycopy(arr, 0, left, 0, arr.length/2);
		System.arraycopy(arr, arr.length/2, right, 0, arr.length-arr.length/2);
		MergeSort(left);
		MergeSort(right);

		// Merge
		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				arr[k++] = left[i++];
			} else {
				arr[k++] = right[j++];
			}
		}

		while (i < left.length) arr[k++] = left[i++];
		while (j < right.length) arr[k++] = right[j++];
	}

	// Source: slides
	public static void recursive_qs(int[] arr, int left, int right) {
		if (right <= left) return;

		// partition,
		int i = left - 1, j = right;
		while (true) {
			while (arr[++i] <= arr[right]) if (i == right) break;
			while (arr[--j] >= arr[right])
				if (j == left || j == i) break;
			if (j <= i) break;
			swap(arr, i, j);
		}
		if (i < right) swap(arr, i, right);
		// recursively sort the left & the right parts
		recursive_qs(arr, left, i-1);
		recursive_qs(arr, i+1, right);
	}

	/**
	 * 
	 * Sorts the numbers present in the file based on the algorithm provided.
	 * 0 = Arrays.sort() (Java Default)
	 * 1 = Bubble Sort
	 * 2 = Selection Sort 
	 * 3 = Insertion Sort 
	 * 4 = Mergesort
	 * 5 = Quicksort
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException { 
		In in = new In(args[0]);

		// Storing file input in an array
		int[] a = in.readAllInts();

		int[] b = a.clone();
		Arrays.sort(b);

		int[] c = b.clone();
		// https://stackoverflow.com/a/2137791/1198896
		for (int i = 0; i < c.length / 2; i++) {
			int tmp = c[i];
			c[i] = c[c.length - i - 1];
			c[c.length - i - 1] = tmp;
		}

		int[] d = b.clone();
		Random r = new Random();
		for (int i = d.length / 10; i > 0; i--) {
			int idx = r.nextInt(d.length),
					idx2 = r.nextInt(d.length),
					tmp = d[idx];
			d[idx] = d[idx2];
			d[idx2] = tmp;
		}
		// So it gets GC'd
		r = null;

		String algorithmUsed;
		switch (args[1]) {
		case "0":
			algorithmUsed = "Arrays.sort()";
			break;
		case "1":
			algorithmUsed = "bubble sort";
			break;
		case "2":
			algorithmUsed = "selection sort";
			break;
		case "3":
			algorithmUsed = "insertion sort";
			break;
		case "4":
			algorithmUsed = "mergesort";
			break;
		case "5":
			algorithmUsed = "quicksort";
			break;
		default:
			algorithmUsed = "unknown";
		}


		String arrayUsed = null;
		for (int _i = 0; _i < 4; _i++) {
			int[] arr = null;
			switch (_i) {
			case 0:
				arr = a;
				arrayUsed = "a";
				break;
			case 1:
				arr = b;
				arrayUsed = "b";
				break;
			case 2:
				arr = c;
				arrayUsed = "c";
				break;
			case 3:
				arr = d;
				arrayUsed = "d";
				break;
			}

			Stopwatch timer = new Stopwatch();

			switch (algorithmUsed) {
			case "Arrays.sort()":
				Arrays.sort(arr);
				break;
			case "bubble sort":
				// XXX test
				boolean finished = false;
				while (!finished) {
					finished = true;
					for (int i = 0; i < arr.length - 1; i++) {
						if (arr[i] > arr[i+1]) {
							int tmp = arr[i+1];
							arr[i+1] = arr[i];
							arr[i] = tmp;
							finished = false;
						}
					}
				}
				break;
			case "selection sort":
				// Source: slides
				int j, k;
				for (int i = 0; i < arr.length - 1; i++) {
					j = i;
					for (k = i+1; k < arr.length; k++) {
						if (arr[k] < arr[j]) j=k;
					}
					if (j != i) swap(arr, i, j);
				}
				break;
			case "insertion sort":
				// Source: slides
				int temp;
				for (int i = 1; i < arr.length; i++) {
					temp = arr[i];
					j = i - 1;
					while (j >= 0 && arr[j] > temp) {
						arr[j+1] = arr[j];
						j--;
					}
					arr[j+1] = temp;
				}
				break;
			case "mergesort":
				MergeSort(arr);
				break;
			case "quicksort":
				recursive_qs(arr, 0, arr.length - 1);
				break;
			}

			double time = timer.elapsedTimeMillis();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String netID = "ajord17";
			StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, args[0]);

			// https://stackoverflow.com/a/2885224/1198896
			PrintWriter writer = new PrintWriter(arrayUsed + ".txt");
			for (int num: arr) {
				writer.println(num);
			}
			writer.close();
		}
	} 
} 


