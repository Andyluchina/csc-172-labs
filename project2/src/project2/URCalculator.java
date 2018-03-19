package project2;

import java.util.ArrayList;
import java.util.Scanner;

public class URCalculator {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		boolean debug = true;

		while (true) {
			System.out.print("> ");
			String line = scanner.nextLine().trim();
			switch (line) {
			case "exit":
				System.exit(0);
				break;
			case "debug enable":
				debug = true;
				break;
			case "debug disable":
				debug = false;
				break;
			default:
				ArrayList<Token> tokens = Lexer.tokenize(line);
				if (debug) System.out.println(tokens);
			}
		}
	}

}
