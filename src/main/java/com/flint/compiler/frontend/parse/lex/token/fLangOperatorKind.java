package com.flint.compiler.frontend.parse.lex.token;

public enum fLangOperatorKind {
	O_STMT_SEP("@004", 20, false),
	O_BRACKETS("@[]", 10, false),
	O_PARENS("@()", 9, false),
	O_ETA_EXPANSION("@_", 9, false),
	O_CURLY_BRACES("@{}", 9, false),
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
	O_EQUAL("==", 4, false),
	O_NOT_EQUAL("!=", 4, false),
	O_LESS("<", 4, false),
	O_LESS_EQUAL("<=", 4, false),
	O_GREATER(">", 4, false),
	O_GREATER_EQUAL(">=", 4, false),
	O_IF("if", 4, false),
	O_ELSE("else", 4, false),
	O_MATCH("match", 3, false),
	O_PIPE("|", 2, false),
	O_COMMA(",", 2, false),
	O_LEFT_PAREN("(", 2, false),
	O_SEMICOLON(";", 1, false),
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

	public static fLangOperatorKind getLogicalOperatorKind(fTokenKind kind) {
		switch (kind) {
			case T_LOGICAL_AND:
				return O_PIPE; // Using O_PIPE for logical AND
			case T_LOGICAL_OR:
				return O_COMMA; // Using O_COMMA for logical OR
//			case T_EXCLAMATION:
//				return O_NOT_EQUAL; // Using O_NOT_EQUAL for logical NOT
			default:
				throw new IllegalArgumentException("Not a logical operator: " + kind);
		}
	}

	public static fLangOperatorKind getRelationalOperatorKind(fTokenKind kind) {
		switch (kind) {
			case T_EQUAL:
				return O_EQUAL;
			case T_NOT_EQUAL:
				return O_NOT_EQUAL;
			case T_LT:
				return O_LESS;
			case T_LTE:
				return O_LESS_EQUAL;
			case T_GT:
				return O_GREATER;
			case T_GTE:
				return O_GREATER_EQUAL;
			default:
				throw new IllegalArgumentException("Not a relational operator: " + kind);
		}
	}
	public static fLangOperatorKind getMathOperatorKind(fTokenKind kind) {
		switch (kind) {
			case T_PLUS:
				return O_PLUS;
			case T_MINUS:
				return O_MINUS;
			case T_STAR:
				return O_MULTIPLY;
			case T_FORWARD_SLASH:
				return O_DIVIDE;
			case T_PERCENT:
				return O_MODULO;
			default:
				throw new IllegalArgumentException("Not a math operator: " + kind);
		}
	}
}
