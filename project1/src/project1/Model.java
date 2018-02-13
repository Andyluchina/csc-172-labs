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
	// This prop is read-only to outside observers
	private static Integer[][] grid;
	public static Integer[][] getGrid() {
		return grid;
	}
	
	private static Random random = new Random();

	public static boolean moveUp() {
		// TODO
		return false;
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
				} else if (pullTo[j] == pullFrom[j]) {
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
	public static void moveDown() {
		if (_moveDown()) {
			addRandomNumber();
		}
	}

	public static boolean moveLeft() {
		// TODO
		return false;
	}

	public static boolean moveRight() {
		// TODO
		return false;
	}

	public static void addRandomNumber() {
		// Note: it's not safe to call this if the board is full

		int candidate;
		if (random.nextBoolean()) {
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
	
	public static void init() {
		grid = new Integer[4][4];

		for (Integer[] arr: grid) {
			Arrays.fill(arr, 0);
		}

		addRandomNumber();
		addRandomNumber();
	}
}
