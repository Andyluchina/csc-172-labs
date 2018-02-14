package project1;

import java.util.Scanner;
import project1.Model;
import project1.View;

public class main {
	public static char lastKeyPressed = ' ';

	public static void main(String[] args) throws Exception {
		Model.init();
		View.render(Model.getGameState(), Model.getGrid(), Model.getMovesMade(), Model.getMoveStatus(), lastKeyPressed);
		boolean gameInProgress = true;

		/*
		 * Apparently in order to eagerly read a character from the console,
		 * instead of waiting for a newline, you need to put the console into
		 * "raw" mode. Also, there apparently is no portable way to do this
		 * built-in to Java. There goes "write once, run anywhere". See
		 * https://stackoverflow.com/a/1066647/1198896.
		 * 
		 * Also, this project does not allow external dependencies. So I can't
		 * get a library to do this, either. FML.
		 * 
		 * Hence, go with the shoddy implementation that requires you to press
		 * enter. This is not the Right Way™.
		 * 
		 * :/
		 * 
		 */
		Scanner scanner = new Scanner(System.in);

		// Poor man's event loop
		while (true) {
			if (Model.getGameState() != "in_progress") {
				gameInProgress = false;
			}

			// https://stackoverflow.com/a/13942707/1198896
			char c = scanner.next().charAt(0);
			lastKeyPressed = c;
			switch (c) {
			case 'w':
				Model.moveUp();
				break;
			case 'a':
				Model.moveLeft();
				break;
			case 's':
				Model.moveDown();
				break;
			case 'd':
				Model.moveRight();
				break;
			case 'q':
				if (gameInProgress) {
					System.out.print("are you sure? (y/n) ");
					if (scanner.next().charAt(0) == 'y') {
						System.exit(0);
					}
				} else {
					System.exit(0);
				}
				break;
			case 'r':
				if (gameInProgress) {
					System.out.print("are you sure? (y/n) ");
					if (scanner.next().charAt(0) == 'y') {
						Model.init();
					}
				} else {
					Model.init();
				}
				break;
			}

			View.render(Model.getGameState(), Model.getGrid(), Model.getMovesMade(), Model.getMoveStatus(), lastKeyPressed);
		}
	}

}
