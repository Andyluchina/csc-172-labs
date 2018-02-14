package project1;

// This module is all statics because it is a module, not a class.
//
// That is, it is not supposed to be initialized with `new`, because
// having more than one model in this application is pointless.

// XXX there are way too many voids here and this would be far better
// written in a functional style, but I am not bothering because Java
// will fight me every damn step of the way.

import java.util.Random;
import java.util.Arrays;

public class Model {
	/*
	 * MODULE-WIDE PROPERTIES
	 */

	// This prop is read-only to outside observers
	private static Integer[][] grid;
	public static Integer[][] getGrid() {
		return grid;
	}

	// Ditto
	private static int movesMade = 0;
	public static int getMovesMade() {
		return movesMade;
	}

	// Once more, with feeling
	private static String moveStatus = "";
	public static String getMoveStatus() {
		return moveStatus;
	}

	private static Random random = new Random();

	/*
	 * MOVEMENT UTILITIES
	 */

	// XXX can we refactor all these dumb stubs?
	// I suspect not without decent first-class functions, but maybe...

	private static boolean _moveUp() {
		boolean moved = false;

		// Counterintuitively, we start on top row and "reach" down
		for (int i = 0; i <= 2; i++) {
			Integer[] pullFrom = grid[i+1];
			Integer[] pullTo = grid[i];

			for (int j = 0; j <= 3; j++) {
				if (pullTo[j] == 0 && pullFrom[j] == 0) {
					continue;
				} else if (pullTo[j] == 0 && pullFrom[j] != 0) {
					// Pull unconditionally
					pullTo[j] = pullFrom[j];
					pullFrom[j] = 0;
					moved = true;
				} else if (pullTo[j].equals(pullFrom[j])) {
					// Add them
					pullTo[j] += pullFrom[j];
					pullFrom[j] = 0;
					moved = true;
				}
			}
		}

		// Keep recursing as long as we moved things
		if (moved) {
			_moveUp();
		}

		// See the remarks in the bottom of _moveDown() about the caller
		// getting `moved`.

		return moved;
	}

	// Stub method that helps us add a tile only once given the recursion
	public static void moveUp() throws Exception {
		if (_moveUp()) {
			if (getGameState() == "in_progress") addRandomNumber();
			movesMade++;
			moveStatus = "valid";
		} else {
			moveStatus = "invalid";
		}
	}

	private static boolean _moveDown() {
		boolean moved = false;

		// Counterintuitively, we start on bottom row and "reach" up
		for (int i = 3; i > 0; i--) {
			Integer[] pullFrom = grid[i-1];
			Integer[] pullTo = grid[i];
			
			for (int j = 0; j <= 3; j++) {
				if (pullTo[j] == 0 && pullFrom[j] == 0) {
					continue;
				} else if (pullTo[j] == 0 && pullFrom[j] != 0) {
					// Pull unconditionally
					pullTo[j] = pullFrom[j];
					pullFrom[j] = 0;
					moved = true;
				} else if (pullTo[j].equals(pullFrom[j])) {
					// Add them
					pullTo[j] += pullFrom[j];
					pullFrom[j] = 0;
					moved = true;
				}
			}
		}

		// Keep recursing as long as we moved things
		if (moved) {
			_moveDown();
		}

		/*
		 * Note that the method caller will get back `moved` from the first
		 * invocation, and we discard this value for all recursive invocations.
		 * 
		 * This is the Right Way(tm) because if the board is going to move at
		 * all, it's *guaranteed* to move on the first try.
		 */

		return moved;
	}

	// Stub method that helps us add a tile only once given the recursion
	public static void moveDown() throws Exception {
		if (_moveDown()) {
			if (getGameState() == "in_progress") addRandomNumber();
			movesMade++;
			moveStatus = "valid";
		} else {
			moveStatus = "invalid";
		}
	}

	private static boolean _moveLeft() {
		boolean moved = false;

		// x
		for (int i = 0; i <= 2; i++) {
			// y
			for (int j = 0; j <= 3; j++) {
				if (grid[j][i] == 0 && grid[j][i+1] == 0) {
					continue;
				} else if (grid[j][i] == 0 && grid[j][i+1] != 0) {
					// Pull unconditionally
					grid[j][i] = grid[j][i+1];
					grid[j][i+1] = 0;
					moved = true;
				} else if (grid[j][i].equals(grid[j][i+1])) {
					// Add them
					grid[j][i] += grid[j][i+1];
					grid[j][i+1] = 0;
					moved = true;
				}
			}
		}

		// Keep recursing as long as we moved things
		if (moved) {
			_moveLeft();
		}

		// See the remarks in the bottom of _moveDown() about the caller
		// getting `moved`.

		return moved;
	}

	// Stub method that helps us add a tile only once given the recursion
	public static void moveLeft() throws Exception {
		if (_moveLeft()) {
			if (getGameState() == "in_progress") addRandomNumber();
			movesMade++;
			moveStatus = "valid";
		} else {
			moveStatus = "invalid";
		}
	}

	private static boolean _moveRight() {
		boolean moved = false;

		// x
		for (int i = 3; i > 0; i--) {
			// y
			for (int j = 0; j <= 3; j++) {
				if (grid[j][i] == 0 && grid[j][i-1] == 0) {
					continue;
				} else if (grid[j][i] == 0 && grid[j][i-1] != 0) {
					// Pull unconditionally
					grid[j][i] = grid[j][i-1];
					grid[j][i-1] = 0;
					moved = true;
				} else if (grid[j][i].equals(grid[j][i-1])) {
					// Add them
					grid[j][i] += grid[j][i-1];
					grid[j][i-1] = 0;
					moved = true;
				}
			}
		}

		// Keep recursing as long as we moved things
		if (moved) {
			_moveRight();
		}

		// See the remarks in the bottom of _moveDown() about the caller
		// getting `moved`.

		return moved;
	}

	// Stub method that helps us add a tile only once given the recursion
	public static void moveRight() throws Exception {
		if (_moveRight()) {
			if (getGameState() == "in_progress") addRandomNumber();
			movesMade++;
			moveStatus = "valid";
		} else {
			moveStatus = "invalid";
		}
	}

	/*
	 * UTILITY METHODS
	 */

	private static boolean boardHasNumber(int nnum) {
		for (Integer[] row: grid) {
			for (Integer num: row) {
				if (num.equals(nnum)) return true;
			}
		}
		return false;
	}

	// XXX enum
	public static String getGameState() {
		if (boardHasNumber(2048)) {
			return "won";
		} else if (!boardHasNumber(0)) {
			return "lost";
		} else {
			return "in_progress";
		}
	}

	public static void addRandomNumber() throws Exception {
		if (getGameState() != "in_progress") {
			throw new Exception("addRandomNumber() called but game is not in progress");
		}

		int candidate;
		if (random.nextFloat() < 0.8) {
			candidate = 2;
		} else {
			candidate = 4;
		}

		// XXX refactor, this can spin for a very long time if the board is mostly full
		Boolean completed = false;
		while (!completed) {
			int x = random.nextInt(4);
			int y = random.nextInt(4);
			if (grid[x][y] == 0) {
				grid[x][y] = candidate;
				completed = true;
			}
		}
	}

	/*
	 * BOARD INIT
	 */

	public static void init() throws Exception {
		grid = new Integer[4][4];

		for (Integer[] arr: grid) {
			Arrays.fill(arr, 0);
		}

		movesMade = 0;
		moveStatus = "unknown";

		// XXX this is for testing, but it would be unnecessary if methods
		// were functional...
		grid[2][0] = 1024;grid[2][1]=1024;
		//addRandomNumber();
		//addRandomNumber();
	}
}
