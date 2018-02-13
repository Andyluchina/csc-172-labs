package project1;

public class View {
	// Copied from lab 3 util
	public static void render(Integer[][] grid) {
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
