package lab3;

import java.util.ArrayList;
import java.util.Arrays;

public class Lab3Task1 {
	
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

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2000,30,400));
		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>();
		finalList.add(0, list);
		finalList.add(1, list);
		finalList.add(2, list);
		finalList.add(3, list);
		
		print2DList(finalList);
		
		Integer[][] finalArray = {
				{10, 15, 30, 40},
				{15, 5, 8, 2}, 
				{20, 2, 4, 2},
				{1, 4, 5, 0}
		};
		
		System.out.println();

		print2DArray(finalArray);
	}

}
