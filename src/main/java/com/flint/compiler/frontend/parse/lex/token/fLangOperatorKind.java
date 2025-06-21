package com.flint.compiler.frontend.parse.lex.token;

public enum fLangOperatorKind {
	O_BRACKETS("@[]", 10, false),
	O_DOT(".", 9, false),
	O_COLON(":", 8, true),
	O_FAT_ARROW("=>", 7, false),
	O_WITH("with", 7, false),
	O_POUND("#", 7, false),
	O_AT("@", 8, true),
	O_MULTIPLY("*", 6, false),
	O_DIVIDE("/", 6, false),
	O_MODULO("%", 6, false),
	O_PLUS("+", 5, false),
	O_MINUS("-", 5, false),
	O_IF("if", 4, false),
	O_ELSE("else", 4, false),
	O_MATCH("match", 3, false),
	O_PIPE("|", 2, false),
	O_COMMA(",", 2, false),
	O_LEFT_PAREN("(", 2, false),
	O_SEMI(";", 1, false),
	O_ID_SMBLC_RIGHT_ASSC("@003", 1, true),
	O_ID_SMBLC_LEFT_ASSC("@002", 1, false),
	O_ASSIGN("=", 1, false),
	O_ROOT("@001", 0, false),

	O_RIGHT_PAREN(")", -1, false);

	fLangOperatorKind(String opname, int precedence, boolean isRightAssociative) {
		assert opname != null && opname.trim().length() > 0;
		this.opname = opname;
		this._prec = precedence;
		this.isRightAssociative = isRightAssociative;
	}
	public  final String opname;
	private final int _prec;
	public final boolean isRightAssociative;
	public int precedence() {
		if(_prec < 0){
			throw new UnsupportedOperationException();
		}
		return _prec;
	}

	public static fLangOperatorKind getIdSymbolicAssoc(boolean isRightAssociative) {
		if(isRightAssociative){
			return  fLangOperatorKind.O_ID_SMBLC_RIGHT_ASSC;
		}
		return   fLangOperatorKind.O_ID_SMBLC_LEFT_ASSC;
	}
}
