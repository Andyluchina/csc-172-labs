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

        //TODO: 
        // Read the second argument and based on input select the sorting algorithm
        //  * 0 = Arrays.sort() (Java Default)
        //  * 1 = Bubble Sort
        //  * 2 = Selection Sort 
        //  * 3 = Insertion Sort 
        //  * 4 = Mergesort
        //  * 5 = Quicksort
        //  Perform sorting on a,b,c,d. Pring runtime for each case along with timestamp and record those. 
        // For runtime and priting, you can use the same code from Lab 4 as follows:
        
         // TODO: For each array, a, b, c, d:  
        Stopwatch timer = new Stopwatch();
        // TODO: Perform Sorting and store the result in an  array

        double time = timer.elapsedTimeMillis();
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
          //TODO: Replace with your own netid
        String netID = "ajord17";
          //TODO: Replace with the algorithm used 
        String algorithmUsed = "insertion sort";
          //TODO: Replace with the  array used 
        String arrayUsed = "a";
          StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, args[0]);
          // Write the resultant array to a file (Each time you run a program 4 output files should be generated. (one for each a,b,c, and d)
		
  } 
} 


