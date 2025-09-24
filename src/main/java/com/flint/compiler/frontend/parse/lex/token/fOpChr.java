package com.flint.compiler.frontend.parse.lex.token;

public enum fOpChr {
	INVALID((char) 0x0),
	COLON(':'),
	ASSIGN('='),
	STAR('*'),
	FORWARD_SLASH('/'),
	PLUS('+'),
	MINUS('-'),
	LT('<'),
	GT('>'),
	PIPE('|'),
	AMPERSAND('&'),
	BANG('!'),
	POUND('#'),
	PERCENT('%'),
	QUESTION('?'),
	AT('@'),
	BACKSLASH('\\'),
	CARET('^'),
	TILDE('~');

	fOpChr(char c) {
		this.opChr = c;
	}

	public final char opChr;
}