package project1;

import java.io.IOException;

public class View {
	// % for i in $(seq 50); do printf '\\r\\n'; done
	final static String FIFTY_NEWLINES = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n";

	// https://stackoverflow.com/a/2979383/1198896#comment41661944_13542582
	private static void clear() {
		System.out.println(FIFTY_NEWLINES);
	}

	// Copied from lab 3 util; lightly modified
	public static void render(Integer[][] grid) {
		clear();

		int middleLinesPrinted = 0;
		System.out.println("┌─────┬─────┬─────┬─────┐");
		for (Integer[] row: grid) {
			System.out.print("│");
			for (Integer num: row) {
				if (num == 0) {
					System.out.print("*   ");
				} else {
					// https://stackoverflow.com/a/391978/1198896
					System.out.print(String.format("%1$-4s", num.toString()));
				}
				System.out.print(' ');
				System.out.print('│');
			}
			System.out.println();
			if (middleLinesPrinted < 3) {
				System.out.println("├─────┼─────┼─────┼─────┤");
				middleLinesPrinted++;
			}
		}
		System.out.println("└─────┴─────┴─────┴─────┘");
	}
}
