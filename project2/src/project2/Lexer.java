package project2;

import java.util.ArrayList;
import project2.Token;

public class Lexer {
	// XXX i18n
	private static final String letters = "abcdefghijklmnopqrstuvwxyz";
	private static final String numbers = "1234567890";
	private static final String symbols = "()+-*/";
	private static final String whitespace = " ";

	public static enum tokens {
			ADD,
			SUB,
			MULT,
			DIV,
			ASSIGN,
			PAREN_START,
			PAREN_END,
			VAR_REF,
			NUM_LITERAL,
			SYNTAX_ERROR,
			EOF
	}

	public static enum errors {
		GENERIC,
		MISMATCHED_PARENS,
		UNKNOWN_CHAR
	}

	private static enum states {
		NORMAL,
		NUM
	}

	public static ArrayList<Token> tokenize(String str) {
		ArrayList<Token> l = new ArrayList<Token>();

		states state = states.NORMAL;

		String num = "";

		int i = 0,
		    parens = 0;

		while (i < str.length()) {
			char c = str.charAt(i);

			switch (state) {
			case NORMAL:
				if (letters.indexOf(c) != -1) {
					Token token = new Token(tokens.VAR_REF);
					token.data = c;
					l.add(token);
					i++;
				} else if (numbers.indexOf(c) != -1) {
					state = states.NUM;
				} else if (symbols.indexOf(c) != -1) {
					Token token;
					switch (c) {
					case '(':
						token = new Token(tokens.PAREN_START);
						break;
					case ')':
						token = new Token(tokens.PAREN_END);
						break;
					case '+':
						token = new Token(tokens.ADD);
						break;
					case '-':
						token = new Token(tokens.SUB);
						break;
					case '*':
						token = new Token(tokens.MULT);
						break;
					case '/':
						token = new Token(tokens.DIV);
						break;
					default:
						// This is just here to satisfy the type checker
						token = new Token(tokens.SYNTAX_ERROR);
						throw new Error();
					}

					l.add(token);
					i++;
				} else if (whitespace.indexOf(c) != -1) {
					i++;
				} else {
					Token token = new Token(tokens.SYNTAX_ERROR);
					token.error = errors.UNKNOWN_CHAR;
					token.data = c;
					l.add(token);
					return l;
				}
				break;
			case NUM:
				if (numbers.indexOf(c) != -1) {
					num += c;
					i++;
				} else {
					Token token = new Token(tokens.NUM_LITERAL);
					token.data = num;
					num = "";
					l.add(token);
					state = states.NORMAL;
				}
				break;
			}
		}

		// Clean up
		if (state.equals(states.NUM)) {
			Token token = new Token(tokens.NUM_LITERAL);
			token.data = num;
			l.add(token);
		}

		l.add(new Token(tokens.EOF));

		return l;
	}
}
