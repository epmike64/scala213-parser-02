package com.flint.compiler.frontend.parse.lex.token;


import static com.flint.compiler.frontend.parse.lex.token.fModifier.fModifierKindTyp.*;

public class fModifier {

	public enum fAccessQualifier {
		ID, THIS
	}

	public enum fModifierKindTyp {
		DEFAULT, LOCAL, ACCESS
	}

	public enum fModifierKind {
		OVERRIDE("override", DEFAULT),
		ABSTRACT("abstract", fModifierKindTyp.LOCAL),
		FINAL("final", LOCAL),
		SEALED("sealed", LOCAL),
		IMPLICIT("implicit", LOCAL),
		LAZY("lazy", LOCAL),
		PRIVATE("private", ACCESS),
		PROTECTED("protected", ACCESS);

		fModifierKind(String n, fModifierKindTyp v) {
			this.modname = n;
			this.modtype = v;
		}
		public final String modname;
		public fModifierKindTyp modtype;
	}
}
