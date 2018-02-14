package project1;

import java.io.IOException;

public class View {
	// % for i in $(seq 50); do printf '\\r\\n'; done
	final static String FIFTY_NEWLINES = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n";

	// https://stackoverflow.com/a/38365871/1198896
	private static void clear() {
		System.out.println(FIFTY_NEWLINES);
	}

	// Copied from lab 3 util; lightly modified
	public static void render(Integer[][] grid) {
		clear();

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
