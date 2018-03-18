package project2;

import java.util.ArrayList;

public class Lexer {
	// XXX i18n
	private static String letters = "abcdefghijklmnopqrstuvwxyz";
	private static String numbers = "1234567890";
	private static String symbols = "()+-*/";

	private class Token {
		public tokens token;
		public errors error;
		// XXX make this type checked or something
		public Object data;
	}

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
			SYNTAX_ERROR
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

	public static ArrayList<tokens> tokenize(String str) {
		ArrayList<tokens> l = new ArrayList<tokens>();

		states state = states.NORMAL;

		String num = "";

		int i = 0,
		    parens = 0;

		while (i < str.length()) {
			char c = str.charAt(i);

			// TODO
			switch (state) {
			case NORMAL:
				if (letters.indexOf(c) != -1) {
					Token token = new Token();
					token.token = tokens.VAR_REF;
					token.data = c;
					i++;
				}

				if (numbers.indexOf(c) != -1) {
					state = states.NUM;
					num += c;
				}
				break;
			case NUM:
				if (numbers.indexOf(c) != -1) {
					state = states.NUM;
					num += c;
				}
			}
		}
	}
}
