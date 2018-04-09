import java.lang.reflect.Array;
import java.util.Arrays;

public class Lab8P4 {
	public static boolean accept(int[] cookies, int k) {
		for (int c: cookies) {
			if (c < k) return false;
		}
		return true;
	}

	public static int[] mix_cookies(int[] cookies) {
		assert cookies.length > 1;
		Arrays.sort(cookies);
		int[] ncookies = new int[cookies.length-1];
		System.arraycopy(cookies, 2, ncookies, 0, cookies.length-2);
		ncookies[ncookies.length-1] = cookies[0] + 2*cookies[1];
		return ncookies;
	}

	public static int Jesse_cookies(int[] cookies, int k) {
		int[] arr = cookies;
		int mixes = 0;
		while (!accept(arr, k)) {
			if (arr.length < 2) {
				return -1;
			}
			mixes++;
			arr = mix_cookies(arr);
			for (int i: arr) System.out.print(i + " ");
			System.out.println();
		}
		return mixes;
	}

	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		System.out.println(Jesse_cookies(arr, 15	));
	}
}
