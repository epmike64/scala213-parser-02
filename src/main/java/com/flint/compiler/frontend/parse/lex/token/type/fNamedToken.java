package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fNamedToken extends fToken {
	/** The name of this token */
	public final String name;

	public fNamedToken(fTokenKind kind, int pos, int endPos, String name) {
		super(kind, pos, endPos);
		this.name = name;
	}

	@Override
	protected void assertIt() {
		assert  kind.tag == fTokenTag.NAMED;
	}

	public boolean isRightAssociative() {
		return name.lastIndexOf(":") == name.length() - 1;
	}

	public void checkKind() {
		if (kind.tag != fTokenTag.NAMED) {
			throw new AssertionError("Bad token kind - expected " + fTokenTag.NAMED);
		}
	}

	@Override
	public String toString() {
		return "NamedToken{" +
				"name='" + name + '\'' +
				", kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}

	@Override
	public String getName() {
		return name;
	}
}