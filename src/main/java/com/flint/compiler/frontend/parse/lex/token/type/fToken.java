package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fToken {

	public final int pos, endPos;
	public final fTokenKind kind;

	public static final fToken SOF = new fToken(fTokenKind.T_SOF, -1, -1);
	public static final fToken ROOT_OPERATOR_TOKEN = new fToken(fTokenKind.T_ROOT_OPERATOR, -1, -1);

	public fToken(fTokenKind kind, int pos, int endPos) {
		this.kind = kind;
		this.pos = pos;
		this.endPos = endPos;
		assertIt();
	}

	protected void assertIt() {
		assert  kind.tag == fTokenTag.KWRD || kind.tag == fTokenTag.OPERATOR || kind.tag == fTokenTag.INTERN;
	}

	public String getName() {
		return kind.tkName;
	}

	public fTokenKind getTKind() {
		return kind;
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
