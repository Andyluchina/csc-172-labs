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
				if (debug) System.out.println("[DBG] Lexer: " + tokens);
				Token last = tokens.get(tokens.size()-1);

				if (last.token.equals(Lexer.tokens.SYNTAX_ERROR)) {
					switch(last.error) {
					case UNKNOWN_CHAR:
						System.out.println("SyntaxError: unexpected or invalid token " + last.data);
						break;
					case MISMATCHED_PARENS:
						System.out.println("SyntaxError: mismatched parenthesis");
						break;
					case GENERIC:
						System.out.println("SyntaxError: unknown error");
						break;
					}

					continue;
				}

				Evaluator.evaluate(tokens);
			}
		}
	}

}
