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

public class Sorting {


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
	public static void main(String[] args)  { 
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
		switch (args[2]) {
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
		for (int i = 0; i < 4; i++) {
			int[] arr = null;
			switch (i) {
			case 0:
				arr = a;
				arrayUsed = "a";
			case 1:
				arr = b;
				arrayUsed = "b";
			case 2:
				arr = c;
				arrayUsed = "c";
			case 3:
				arr = d;
				arrayUsed = "d";
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
					for (i = 0; i < arr.length - 1; i++) {
						if (arr[i] > arr[i+1]) {
							int tmp = arr[i+1];
							arr[i+1] = arr[i];
							arr[i] = tmp;
							finished = false;
						}
					}
				}
			case "selection sort":
				// lower is lower bound
				for (int lower = 0; lower < arr.length; lower++) {
					int lowest = arr[lower],
						lowestIdx = lower;
					for (i = lower; i < arr.length; i++) {
						if (lowest > arr[i]) {
							lowest = arr[i];
							lowestIdx = i;
						}
					}

					// TODO
				}
			case "insertion sort":
				// TODO
			case "mergesort":
				// TODO
			case "quicksort":
				// TODO
			}

			double time = timer.elapsedTimeMillis();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String netID = "ajord17";
			StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, args[0]);
			// Write the resultant array to a file (Each time you run a program 4 output files should be generated. (one for each a,b,c, and d)
		}



	} 
} 


