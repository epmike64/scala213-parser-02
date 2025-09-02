package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.OpChar;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;

public class fToken {

	public final int pos, endPos;
	public final fTokenKind kind;

	public static final fToken SEMICOLON = new fToken(fTokenKind.T_SEMICOLON, -1, -1);
	public static final fToken ENTITY_SEP = new fToken(fTokenKind.T_ENTITY_SEP, -1, -1);

	public fToken(fTokenKind kind, int pos, int endPos) {
		this.kind = kind;
		this.pos = pos;
		this.endPos = endPos;
	}

	public String name() {
		return kind.tkName;
	}

	public String stringVal() {
		throw new UnsupportedOperationException();
	}

	public int radix() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "Token{" +
				"kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}
}
