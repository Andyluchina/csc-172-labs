package lab3;

public class Lab3Task2 {

	public static void runningSum2DArray(Integer[][] arr, int dir) {
		switch (dir) {
		case 1:
			// Left
			for (Integer[] row: arr) {
				Integer lastNum = null;
				for (int i = 0; i < row.length; i++) {
					if (lastNum == null) {
						// Start of row
						lastNum = row[i];
					} else {
						row[i] += lastNum;
						lastNum = row[i];
					}
				}
			}
			break;
		case 2:
			// Right
			for (Integer[] row: arr) {
				// TODO: refactor
				Integer lastNum = null;
				for (int i = row.length-1; i >= 0; i--) {
					if (lastNum == null) {
						// Start of row
						lastNum = row[i];
					} else {
						row[i] += lastNum;
						lastNum = row[i];
					}
				}
			}
			break;
		case 3:
			// Up
			// This is more brittle than the rest because it assumes 4x4.
			// That's in the spec though so it's fine for me to not bother.
			// Which is good, because I can't be bothered to make it flexible.
			//
			// For every column, starting at the left...
			for (int i = 0; i < 4; i++) {
				int lastNum = 0;
				// ...and for every row, starting at the bottom...
				for (int j = 3; j >= 0; j--) {
					// Add the previous number
					//
					// Note that i and j are ordered in a different way than
					// you'd usually expect.
					arr[j][i] += lastNum;
					lastNum = arr[j][i];
				}
			}
			break;
		case 4:
			// Down
			//
			// See the remarks above about 4x4 assumptions.
			// TODO refactor this to share code with the up direction

			// For every column, starting at the left...
			for (int i = 0; i < 4; i++) {
				int lastNum = 0;
				// ...and for every row, starting at the top...
				for (int j = 0; j <= 3; j++) {
					// Add the previous number
					//
					// Note that i and j are ordered in a different way than
					// you'd usually expect.
					arr[j][i] += lastNum;
					lastNum = arr[j][i];
				}
			}
			break;
		default:
			throw new Error("Invalid direction integer provided");
		}
		
		Lab3Util.print2DArray(arr);
	}
	
	public static void main(String[] args) {
		Integer[][] arr = {
				{10, 15, 30, 40},
				{15, 5, 8, 2}, 
				{20, 2, 4, 2},
				{1, 4, 5, 0}
		};
		
		runningSum2DArray(arr, 4);
	}

}
