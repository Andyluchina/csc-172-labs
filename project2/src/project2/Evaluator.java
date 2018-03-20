package project2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import project2.Lexer.tokens;

public class Evaluator {
	private static enum precedence {
		HIGHER,
		LOWER,
		EQUAL
	}

	// HIGHER means a is higher than b
	private static precedence higherPrecedence(Token a, Token b) {
		if (b.token.equals(Lexer.tokens.ASSIGN)) return precedence.LOWER;
		// Just for implementation simplicity
		if (a.token.equals(Lexer.tokens.PAREN_START)) return precedence.HIGHER;

		if (a.token.equals(Lexer.tokens.DIV) || a.token.equals(Lexer.tokens.MULT)) {
			if (b.token.equals(Lexer.tokens.DIV) || b.token.equals(Lexer.tokens.MULT)) {
				return precedence.EQUAL;
			} else {
				return precedence.HIGHER;
			}
		}

		if (a.token.equals(Lexer.tokens.ADD) || a.token.equals(Lexer.tokens.SUB)) {
			if (b.token.equals(Lexer.tokens.ADD) || b.token.equals(Lexer.tokens.SUB)) {
				return precedence.EQUAL;
			} else {
				return precedence.LOWER;
			}
		}

		// TODO I'm not sure if this is the right thing to do
		return precedence.LOWER;
	}

	private static void opToPostfix(Stack<Token> postfix, Stack<Token> op, Token t) {
		// Note that this method will never be called when higherPrecedence() would return LOWER
		precedence p = higherPrecedence(t, op.peek()).equals(precedence.EQUAL)
				? precedence.EQUAL : precedence.LOWER;
		while (!op.empty() && higherPrecedence(t, op.peek()).equals(p)) {
			postfix.push(op.pop());
		}
		if (!op.empty() && op.peek().token.equals(Lexer.tokens.PAREN_START)) op.pop();
	}

	private static void handleSymbol(Stack<Token> postfix, Stack<Token> op, Token t) {
		if (op.empty() || op.peek().token.equals(Lexer.tokens.PAREN_START)) {
			op.add(t);
		} else {
			if (!higherPrecedence(op.peek(), t).equals(precedence.LOWER)) {
				opToPostfix(postfix, op, t);
			}
			op.push(t);
		}
	}

	public static void evaluate(ArrayList<Token> tokens, Map<String, Double> env, boolean debug) {
		Stack<Token> postfix = new Stack<Token>();
		Stack<Token> op = new Stack<Token>();

		for (Token t: tokens) {
			switch(t.token) {
			case NUM_LITERAL:
				postfix.push(t);
				break;
			case VAR_REF:
				postfix.push(t);
				break;
			case PAREN_START:
				op.push(t);
				break;
			case PAREN_END:
				while (!op.peek().token.equals(Lexer.tokens.PAREN_START)) {
					postfix.push(op.pop());
				}
				// The first PAREN_START
				op.pop();
				break;
			case ADD:
				handleSymbol(postfix, op, t);
				break;
			case SUB:
				handleSymbol(postfix, op, t);
				break;
			case MULT:
				handleSymbol(postfix, op, t);
				break;
			case DIV:
				handleSymbol(postfix, op, t);
				break;
			case ASSIGN:
				handleSymbol(postfix, op, t);
				break;
			case SYNTAX_ERROR:
				throw new Error("Received syntax error in Evaluator");
			case EOF:
				// Cleanup
				while (!op.empty()) {
					postfix.push(op.pop());
				}
				if (debug) System.out.println("[DBG] Postfix: " + postfix);

				// Reverse the postfix
				Stack<Token> _postfix = new Stack<Token>();
				while (!postfix.empty()) {
					_postfix.push(postfix.pop());
				}
				postfix = _postfix;

				// And evaluate.
				Stack<Token> backfill = new Stack<Token>();
				while (!postfix.empty()) {
					Token c = postfix.pop();
					if (c.token.equals(Lexer.tokens.NUM_LITERAL) || c.token.equals(Lexer.tokens.VAR_REF)) {
						backfill.push(c);
					} else {
						assert !c.token.equals(Lexer.tokens.NUM_LITERAL);
						Token n = new Token(Lexer.tokens.NUM_LITERAL);
						Token b = null;
							
						try {
							switch (c.token) {
							case ADD:
								b = backfill.pop();
								n.data = maybeLookup(backfill.pop(), env) + maybeLookup(b, env);
								break;
							case SUB:
								b = backfill.pop();
								n.data = maybeLookup(backfill.pop(), env) - maybeLookup(b, env);
								break;
							case MULT:
								b = backfill.pop();
								n.data = maybeLookup(backfill.pop(), env) * maybeLookup(b, env);
								break;
							case DIV:
								b = backfill.pop();
								n.data = maybeLookup(backfill.pop(), env) / maybeLookup(b, env);
								break;
							case ASSIGN:
								b = backfill.pop();
								env.put((String) backfill.pop().data, maybeLookup(b, env));
								break;
							case EOF:
								break;
							default:
								throw new Error();
							}
							backfill.push(n);
						} catch (ReferenceError e) {
							System.out.println("ReferenceError: " + e.getVariableName() + " does not exist");
						}
					}
				}
				assert backfill.size() == 1;
				try {
					Double result = maybeLookup(backfill.peek(), env);
					if (result != null) System.out.println(result);
				} catch (ReferenceError e) {
					System.out.println("ReferenceError: " + e.getVariableName());
				}
				break;
			}
		}
	}

	public static Double maybeLookup(Token t, Map<String, Double> env) throws ReferenceError {
		if (t.token == tokens.VAR_REF) {
			String key = (String) t.data;
			if (!(env.containsKey(key))) {
				throw new ReferenceError(key);
			}
			return env.get((String) t.data);
		}
		return (Double) t.data;
	}
	
	public static class ReferenceError extends Exception {
		protected final String varname;
		
		public ReferenceError(String varname) {
			super(String.format("undefined variable: %s", varname));
			this.varname = varname;
		}
		
		public String getVariableName() {
			return this.varname;
		}
	}
}
