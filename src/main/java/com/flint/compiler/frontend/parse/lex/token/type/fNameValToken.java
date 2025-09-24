package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fNameValToken extends fToken {

	public fNameValToken(fTokenKind kind, int pos, int endPos, String nameVal) {
		super(kind, pos, endPos, nameVal);
	}

	@Override
	protected void assertIt() {
		assert  kind.tag == fTokenTag.NAME_VAL;
	}

	public boolean isRightAssociative() {
		return tokValue.lastIndexOf(":") == tokValue.length() - 1;
	}

	@Override
	public String toString() {
		return "NamedToken{" +
				"name='" + tokValue + '\'' +
				", kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}
}