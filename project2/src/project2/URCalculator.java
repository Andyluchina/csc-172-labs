package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class URCalculator {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		boolean debug = false;

		Map<String, Double> vars = new HashMap<String, Double>();

		while (true) {
			System.out.print("> ");
			String line = scanner.nextLine().trim();
			if (line.startsWith("clear ")) {
				String target = line.substring(6, line.length());
				if (target.equals("all")) {
					vars.clear();
				} else if (target.isEmpty()) {
					System.out.println("SyntaxError: don't know what to clear");
				} else {
					if (!vars.containsKey(target)) {
						System.out.println("ReferenceError: key " + target + " does not exist");
					}
					vars.remove(target);
				}
				continue;
			}
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
			case "show all":
				for (Map.Entry<String, Double> e: vars.entrySet()) {
					System.out.println(e.getKey() + ": " + e.getValue());
				}
				break;
			case "clear":
				System.out.println("SyntaxError: don't know what to clear");
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
					case UNEXPECTED_TOKEN:
						System.out.println("SyntaxError: unexpected token " + last.data);
						break;
					case MISSING_TOKEN:
						System.out.println("SyntaxError: expected token, saw EOF");
						break;
					case GENERIC:
						System.out.println("SyntaxError: unknown error");
						break;
					}

					continue;
				}

				try {
					Evaluator.evaluate(tokens, vars, debug);
				} catch (ArithmeticException e) {
					System.out.println("RuntimeError: attempt to divide by zero");
				}
			}
		}
	}

}
