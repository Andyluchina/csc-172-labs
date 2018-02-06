package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import lab3.Lab3Util;

public class Lab3Task1 {
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2000,30,400));
		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>();
		finalList.add(0, list);
		finalList.add(1, list);
		finalList.add(2, list);
		finalList.add(3, list);
		
		Lab3Util.print2DList(finalList);
		
		Integer[][] finalArray = {
				{10, 15, 30, 40},
				{15, 5, 8, 2}, 
				{20, 2, 4, 2},
				{1, 4, 5, 0}
		};
		
		System.out.println();

		Lab3Util.print2DArray(finalArray);
	}

}
