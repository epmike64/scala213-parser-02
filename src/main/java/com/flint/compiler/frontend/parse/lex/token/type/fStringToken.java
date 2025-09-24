package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fStringToken extends fToken {


	public fStringToken(fTokenKind kind, int pos, int endPos, String stringVal) {
		super(kind, pos, endPos, stringVal);
	}

	@Override
	protected void assertIt() {
		assert kind.tokTag == fTokenTag.STRING;
	}

	@Override
	public String toString() {
		return "StringToken{" +
				"stringVal='" + tokValue + '\'' +
				", kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}
}