package lab3;

import java.util.ArrayList;

public class Lab3Util {
	public static void print2DList(ArrayList<ArrayList<Integer>> grid) {
		for (ArrayList<Integer> row: grid) {
			for (Integer num: row) {
				// https://stackoverflow.com/a/391978/1198896
				System.out.print(String.format("%1$-4s", num.toString()));
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public static void print2DArray(Integer[][] grid) {
		for (Integer[] row: grid) {
			for (Integer num: row) {
				// https://stackoverflow.com/a/391978/1198896
				System.out.print(String.format("%1$-4s", num.toString()));
				System.out.print(' ');
			}
			System.out.println();
		}
	}

}
