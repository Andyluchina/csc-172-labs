package project2;

import project2.Lexer.errors;
import project2.Lexer.tokens;

public class Token {
	public String toString() {
		return token + (data != null ? "<" +
				(error != null ? error + ", " : "") +
				data + ">" : "");
	}

	public Token(tokens token) {
		this.token = token;
	}

	public tokens token;
	public errors error;
	// XXX make this type checked or something
	public Object data;
}
