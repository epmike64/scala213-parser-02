package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fStringToken extends fToken {
	/** The string value of this token */
	public final String stringVal;

	public fStringToken(fTokenKind kind, int pos, int endPos, String stringVal) {
		super(kind, pos, endPos);
		this.stringVal = stringVal;
		assertIt();
	}

	protected void assertIt() {
		assert kind.tag == fTokenTag.STRING;
	}

	@Override
	public String toString() {
		return "StringToken{" +
				"stringVal='" + stringVal + '\'' +
				", kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}
}