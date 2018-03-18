package lab6;

import java.util.ArrayList;
import java.util.Arrays;

public class EightQueen {
	private static boolean inBounds(int x, int y) {
		if (x < 7 && x >= 0 && y < 7 && y >= 0) return true;
		return false;
	}

	private static boolean acceptanceTest(Boolean[][] board) {
		// XXX assert that there's 8 queens
		for (int qy = 0; qy < 7; qy++) {
			for (int qx = 0; qx <= 7; qx++) {
				if (board[qy][qx].equals(false)) continue;

				// Found a queen

				// Check that the row has only one queen
				boolean queenInRow = false;
				for (Boolean q: board[qy]) {
					if (q.equals(true)) {
						if (queenInRow) {
							return false;
						} else {
							queenInRow = true;
						}
					}
				}

				// Check that the column has only one queen
				boolean queenInColumn = false;
				for (int y = 0; y < 7; y++) {
					if (board[y][qx].equals(true)) {
						if (queenInColumn) {
							return false;
						} else {
							queenInColumn = true;
						}
					}
				}

				// Check diagonals
				// Up and right
				int x = qx, y = qy;
				while (inBounds(x+1, y-1)) {
					x++;
					y--;
					if (board[y][x].equals(true)) return false;
				}
				// Up and left
				x = qx; y = qy;
				while (inBounds(x-1, y-1)) {
					x--;
					y--;
					if (board[y][x].equals(true)) return false;
				}
				// Down and right
				x = qx; y = qy;
				while (inBounds(x+1, y+1)) {
					x++;
					y++;
					if (board[y][x].equals(true)) return false;
				}
				// Down and left
				x = qx; y = qy;
				while (inBounds(x-1, y+1)) {
					x--;
					y++;
					if (board[y][x].equals(true)) return false;
				}
			}
		}

		return true;
	}

	private static void draw(Boolean[][] grid) {
		// Taken from project 1
		int middleLinesPrinted = 0;
		System.out.println("┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐");
		for (Boolean[] row: grid) {
			System.out.print("│");
			for (Boolean hasQueen: row) {
				if (hasQueen) {
					System.out.print("  Q ");
				} else {
					System.out.print("  - ");
				}
				System.out.print(' ');
				System.out.print('│');
			}
			System.out.println();
			if (middleLinesPrinted < 7) {
				System.out.println("├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
				middleLinesPrinted++;
			}
		}
		System.out.println("└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
	}

	private static void advanceQueenInRow(Boolean[] row) {
		for (int i = 0; i < 7; i++) {
			if (row[i].equals(true)) {
				row[i] = false;
				row[i+1] = true;
				return;
			}
		}
	}

	private static void findCompletedAndReset(Boolean[][] board) {
		/*
		 *  Find the latest row that's been "completed" (i.e.
		 *  the queen is at the end of the row) and reset it,
		 *  then advance the previous row.
		 *  
		 *  We don't handle a couple top row edge cases
		 *  because a solution would've been found by then.
		 */

		for (int i = 7; i > 0; i--) {
			if (board[i][7].equals(true)) {
				board[i][7] = false;
				board[i][0] = true;
				if (board[i-1][7].equals(true)) {
					findCompletedAndReset(board);
					return;
				} else {
					// Find where the queen is in the row and advance it
					Boolean[] row = board[i-1];
					advanceQueenInRow(row);
					return;
				}
			}
		}

		advanceQueenInRow(board[7]);
	}

	private static void mutateBoard(Boolean[][] board) {
		/*
		 * I originally wrote this recursively but had to change
		 * to iteratively because I got a stack overflow error.
		 * 
		 * This would be a non-issue if Java had proper tail call
		 * optimization and frankly I'm kind of pissed that it
		 * doesn't.
		 * 
		 * 0/10, would not recommend.
		 */

		findCompletedAndReset(board);

		while (!acceptanceTest(board)) {
			findCompletedAndReset(board);
		}
	}

	public static void main(String[] args) {
		Boolean[][] board = new Boolean[8][8];

		for (Boolean[] arr: board) {
			Arrays.fill(arr, false);
		}

		for (int i = 0; i <= 7; i++) {
			board[i][0] = true;
		}

		/*
		board[1][3] = true;
		board[2][6] = true;
		board[3][0] = true;
		board[4][7] = true;
		board[5][1] = true;
		board[6][4] = true;
		board[7][2] = true;
		*/
		// System.out.println(acceptanceTest(board));

		mutateBoard(board);

		draw(board);
	}
}
