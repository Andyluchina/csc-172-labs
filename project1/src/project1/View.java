package project1;

import java.io.IOException;

public class View {
	// % for i in $(seq 50); do printf '\\r\\n'; done
	final static String FIFTY_NEWLINES = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n";

	// https://stackoverflow.com/a/2979383/1198896#comment41661944_13542582
	private static void clear() {
		System.out.println(FIFTY_NEWLINES);
	}

	private static int findMaxNumber(Integer[][] grid) {
		int max = 0;

		for (Integer[] row: grid) {
			for (int num: row) {
				if (num > max) max = num;
			}
		}

		return max;
	}

	// Copied from lab 3 util; lightly modified
	public static void render(Integer[][] grid, int movesMade, String moveStatus, char lastKeyPressed) throws Exception {
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
		System.out.print("moves made: ");
		System.out.print(movesMade);
		System.out.print(", max number: ");
		System.out.println(findMaxNumber(grid));
		switch (moveStatus) {
		case "unknown":
			System.out.println("");
			break;
		case "valid":
			System.out.println("last move was valid");
			break;
		case "invalid":
			System.out.println("last move was invalid");
			break;
		default:
			throw new Exception("invalid move status " + moveStatus);
		}
		System.out.println("keys: (w), (a), (s), (d),");
		System.out.println("      (r)estart, (q)uit");
		System.out.print("last key pressed: ");
		System.out.println(lastKeyPressed);
		System.out.print("waiting for input: ");
	}
}
