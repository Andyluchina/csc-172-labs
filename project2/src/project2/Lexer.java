package project2;

import java.util.ArrayList;
import project2.Token;

public class Lexer {
	// XXX i18n
	private static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String numbers = "1234567890.";
	private static final String symbols = "()+-*/=";
	private static final String lparens = "{[";
	private static final String rparens = "}]";
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
		UNKNOWN_CHAR,
		UNEXPECTED_TOKEN,
		MISSING_TOKEN
	}

	private static enum states {
		NORMAL,
		NUM,
		VAR
	}

	public static ArrayList<Token> tokenize(String str) {
		ArrayList<Token> l = new ArrayList<Token>();

		states state = states.NORMAL;

		String num = "",
				var = "";

		int i = 0,
		    parens = 0;

		if (str.charAt(0) == '*'
				|| str.charAt(0) == '/'
				|| str.charAt(0) == '=') {
			Token token = new Token(tokens.SYNTAX_ERROR);
			token.error = errors.UNEXPECTED_TOKEN;
			token.data = str.charAt(0);
			l.add(token);
			return l;
		}

		if (str.charAt(0) == '-'
				|| str.charAt(0) == '+') {
			Token token = new Token(tokens.NUM_LITERAL);
			token.data = 0;
			l.add(token);
		}

		if (symbols.indexOf(str.charAt(str.length()-1)) != -1) {
			Token e = new Token(tokens.SYNTAX_ERROR);
			e.error = errors.MISSING_TOKEN;
			l.add(e);
			return l;
		}

		while (i < str.length()) {
			char c = str.charAt(i);

			if (lparens.indexOf(c) != -1) c = '(';
			if (rparens.indexOf(c) != -1) c = ')';

			switch (state) {
			case NORMAL:
				if (letters.indexOf(c) != -1) {
					state = states.VAR;
				} else if (numbers.indexOf(c) != -1) {
					state = states.NUM;
				} else if (symbols.indexOf(c) != -1) {
					Token token;
					switch (c) {
					case '(':
						if (!l.isEmpty()
								&& (l.get(l.size()-1).token.equals(tokens.NUM_LITERAL)
								    || l.get(l.size()-1).token.equals(tokens.VAR_REF))) {
							l.add(new Token(tokens.MULT));
						}
						token = new Token(tokens.PAREN_START);
						parens++;
						break;
					case ')':
						token = new Token(tokens.PAREN_END);
						parens--;
						break;
					case '+':
						if (!l.isEmpty() && l.get(l.size()-1).token.equals(tokens.ADD)) {
							l.remove(l.size()-1);
						}
						token = new Token(tokens.ADD);
						break;
					case '-':
						if (!l.isEmpty()) {
							if (l.get(l.size()-1).token.equals(tokens.ADD)) {
								l.remove(l.size()-1);
							} else if (l.get(l.size()-1).token.equals(tokens.SUB)) {
								l.remove(l.size()-1);
								l.add(new Token(tokens.ADD));
								i++;
								continue;
							}
						}
						token = new Token(tokens.SUB);
						break;
					case '*':
						token = new Token(tokens.MULT);
						break;
					case '/':
						token = new Token(tokens.DIV);
						break;
					case '=':
						token = new Token(tokens.ASSIGN);
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
					// Handle e.g. `1 + 1 1 + 1`
					if (!l.isEmpty() && l.get(l.size()-1).token.equals(tokens.NUM_LITERAL)) {
						Token token = new Token(tokens.SYNTAX_ERROR);
						token.error = errors.UNEXPECTED_TOKEN;
						token.data = str.charAt(i);
						l.add(token);
						return l;
					}
					Token token = new Token(tokens.NUM_LITERAL);
					token.data = Double.parseDouble(num);
					num = "";
					l.add(token);
					state = states.NORMAL;
				}
				break;
			case VAR:
				if (letters.indexOf(c) != -1) {
					var += c;
					i++;
				} else {
					Token token = new Token(tokens.VAR_REF);
					token.data = var;
					var = "";
					l.add(token);
					state = states.NORMAL;
				}
				break;
			}
		}
		// Clean up
		if (state.equals(states.NUM)) {
			Token token = new Token(tokens.NUM_LITERAL);
			token.data = Double.parseDouble(num);
			l.add(token);
		}
		if (state.equals(states.VAR)) {
			Token token = new Token(tokens.VAR_REF);
			token.data = var;
			l.add(token);
		}

		if (parens != 0) {
			Token token = new Token(tokens.SYNTAX_ERROR);
			token.error = errors.MISMATCHED_PARENS;
			l.add(token);
			return l;
		}

		l.add(new Token(tokens.EOF));

		return l;
	}
}
