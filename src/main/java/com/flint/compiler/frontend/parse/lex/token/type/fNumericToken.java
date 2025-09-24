package com.flint.compiler.frontend.parse.lex.token.type;

import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenTag;

public class fNumericToken extends fStringToken {
	/** The 'radix' value of this token */
	public final int radix;

	public fNumericToken(fTokenKind kind, int pos, int endPos, String stringVal, int radix) {
		super(kind, pos, endPos, stringVal);
		this.radix = radix;
	}

	@Override
	protected void assertIt() {
		assert kind.tag == fTokenTag.NUMERIC;
	}

	@Override
	public int radix() {
		return radix;
	}

	@Override
	public String toString() {
		return "NumericToken{" +
				"radix=" + radix +
				", stringVal='" + tokValue + '\'' +
				", kind=" + kind +
				", pos=" + pos +
				", endPos=" + endPos +
				'}';
	}
}