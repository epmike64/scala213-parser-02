package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.OpChar;
import com.flint.compiler.frontend.parse.lex.token.fOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fOperatorMap;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import static com.flint.compiler.frontend.parse.lex.token.fTokenKind.*;

public class ParseHelp {

	private fToken prevToken;
	private fToken token;
	private fLexer lexer;

	ParseHelp(fLexer lexer) {
		this.lexer = lexer;
		this.token = lexer.nextToken();
	}

	fToken getToken() {
		return token;
	}

	fToken getPrevToken() {
		return prevToken;
	}

	fTokenKind getTokenKind() {
		return token.kind;
	}

	fToken next() {
		prevToken = token;
		token = lexer.nextToken();
		System.out.println("Token: " + token);
		return prevToken;
	}

	void acceptOpChar(OpChar opChar) {
		if (token.opChar() == opChar) {
			next();
		} else {
			throw new AssertionError("Expected " + opChar + " but found " + token.opChar());
		}
	}

	fToken accept(fTokenKind kind) {
		if (token.kind != kind) {
			throw new AssertionError("Expected " + kind + " but found " + token.kind);
		}
		return next();
	}

	fToken lookAhead(int n) {
		return lexer.lookAhead(n);
	}

	boolean isLa(int la, fTokenKind... types) {
		fToken laToken = lookAhead(la);
		for (fTokenKind type : types) {
			if (laToken.kind == type) {
				return true;
			}
		}
		return false;
	}

	boolean isLaIdOpChar(int la, OpChar... ops) {
		return isLaOpChar(la, T_ID, ops);
	}

	boolean isLaOpChar(int la, fTokenKind tk, OpChar... ops) {
		fToken laTok = lookAhead(la);
		if(laTok.kind == tk && laTok.opChar() != OpChar.INVALID) {
			for (OpChar op : ops) {
				if (laTok.opChar() == op) {
					return true;
				}
			}
		}
		return false;
	}

	boolean isColonOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.COLON);
	}

	boolean isAssignOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.ASSIGN);
	}

	boolean isPipeOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.PIPE);
	}

	fOperatorKind getOperatorKind(fToken token) {
		return fOperatorMap.getOperatorKind(token);
	}

	int skipLPar() {
		int count = 0;
		while (token.kind == fTokenKind.T_LPAREN) {
			count++;
			next();
		}
		return count;
	}

	int skipRPar() {
		int count = 0;
		while (token.kind == fTokenKind.T_RPAREN) {
			count++;
			next();
		}
		return count;
	}

	void skipSemi() {
		while (token.kind == T_SEMI || token.kind == T_NL) {
			next();
		}
	}

	void skipNL() {
		while (token.kind == T_NL) {
			next();
		}
	}

	void expectOneOf(int la, fTokenKind... types) {
		assert types.length > 0;
		fToken _laToken = lookAhead(la);
		for (fTokenKind type : types) {
			if (_laToken.kind == type) {
				return;
			}
		}
		throw new RuntimeException("Unexpected token: " + token.kind);
	}
}
