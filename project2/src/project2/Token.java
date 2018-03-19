package project2;

import project2.Lexer.errors;
import project2.Lexer.tokens;

public class Token {
	public String toString() {
		String s = "";
		if (data != null || error != null) {
			s += "<";
			if (error != null) {
				s += error + (data != null ? ", " + data : "");
			} else {
				s += data;
			}
			s += ">";
		}

		return token + s;
	}

	public Token(tokens token) {
		this.token = token;
	}

	public tokens token;
	public errors error;
	// XXX make this type checked or something
	public Object data;
}
