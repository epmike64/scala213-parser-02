package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fToken {

	public static final String _undef = "_undef";
	public final int pos, endPos;
	public final fTokenKind kind;
	protected final String tokValue;

	public static final fToken F_OFFSET = new fToken(fTokenKind.T_F_OFFSET, -1, -1, "SOF");
	public static final fToken ROOT_OPERATOR = new fToken(fTokenKind.T_ROOT_OPERATOR, -1, -1, "ROOT_OPERATOR");

	public fToken(fTokenKind kind, int pos, int endPos, String tokValue) {
		this.kind = kind;
		this.pos = pos;
		this.endPos = endPos;
		this.tokValue = tokValue;
		assertIt();
	}

	protected void assertIt() {
		assert  kind.tokTag == fTokenTag.KWRD || kind.tokTag == fTokenTag.OPERATOR || kind.tokTag == fTokenTag.INTERN;
	}

	public String getTokValue() {
		return tokValue;
	}

	public fTokenKind getTKind() {
		return kind;
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
