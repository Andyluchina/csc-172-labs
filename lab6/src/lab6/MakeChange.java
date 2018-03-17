package lab6;

import java.util.ArrayList;

// XXX does not handle 0 cents, negative cents

public class MakeChange {
	// XXX refactor this so each coin has a separate list; it's more efficient
	private static int compute(int count, ArrayList<Integer> coins) {
		if (coins.contains(25)) {
			// Need this cast since otherwise we're passing an index
			coins.remove((Integer) 25);
			coins.add(10);
			coins.add(10);
			coins.add(5);
			return compute(count+1, coins);
		}

		if (coins.contains(10)) {
			coins.remove((Integer) 10);
			coins.add(5);
			coins.add(5);
			return compute(count+1, coins);
		}

		if (coins.contains(5)) {
			coins.remove((Integer) 5);
			coins.add(1);
			coins.add(1);
			coins.add(1);
			coins.add(1);
			coins.add(1);
			return compute(count+1, coins);
		}

		// XXX put in an assertion here that says all elements are 1
		// I tried but Java doesn't freaking have map/reduce so it was ugly.
		return count+1;
	}

	public static int makeChange(int n) {
		// Floor to the greatest multiple of 5 < n
		// We do this because the remainder *has* to be done with pennies so it isn't interesting
		n = n - (n % 5);

		ArrayList<Integer> coins = new ArrayList<Integer>();

		while (n - 25 >= 0) {
			n = n - 25;
			coins.add(25);
		}

		while (n - 10 >= 0) {
			n = n - 10;
			coins.add(10);
		}

		while (n - 5 >= 0) {
			n = n - 5;
			coins.add(5);
		}

		assert n == 0;

		return compute(0, coins);
	}

	public static void main(String[] args) {
		System.out.println(makeChange(12));
	}
}
