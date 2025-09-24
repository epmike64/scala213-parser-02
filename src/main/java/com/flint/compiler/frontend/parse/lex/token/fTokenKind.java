package com.flint.compiler.frontend.parse.lex.token;


public enum fTokenKind {
	T_SOF(fTokenTag.INTERN),
	T_EOF(fTokenTag.INTERN),
	T_ERROR(fTokenTag.INTERN),
	T_NL(fTokenTag.INTERN),
	T_ROOT_OPERATOR(fTokenTag.INTERN),

	T_CLASS_QUALIFIER(fTokenTag.NAME_VAL),
	T_ID(fTokenTag.NAME_VAL),
	T_INT_LIT(fTokenTag.NUMERIC),

	T_FLOAT_LIT(fTokenTag.NUMERIC),

	T_CHAR_LIT(fTokenTag.NUMERIC),
	T_STRING_LIT(fTokenTag.STRING),

	T_ABSTRACT("abstract",   fTokenTag.KWRD),
	T_CASE("case",   fTokenTag.KWRD),
	T_CATCH("catch",   fTokenTag.KWRD),
	T_CLASS("class",   fTokenTag.KWRD),
	T_DEF("def",   fTokenTag.KWRD),
	T_DO("do",   fTokenTag.KWRD),
	T_ELSE("else",   fTokenTag.KWRD),
	T_EXTENDS("extends",   fTokenTag.KWRD),
	T_FALSE("false",   fTokenTag.KWRD),
	T_FINALLY("finally",   fTokenTag.KWRD),
	T_FINAL("final",   fTokenTag.KWRD),
	T_FOR("for",   fTokenTag.KWRD),
	T_IF("if",   fTokenTag.KWRD),
	T_IMPLICIT("implicit",   fTokenTag.KWRD),
	T_IMPORT("import",   fTokenTag.KWRD),
	T_LAZY("lazy",   fTokenTag.KWRD),
	T_MATCH("match",   fTokenTag.KWRD),
	T_NEW("new",   fTokenTag.KWRD),
	T_NULL("null",   fTokenTag.KWRD),
	T_OBJECT("object",   fTokenTag.KWRD),
	T_OVERRIDE("override",   fTokenTag.KWRD),
	T_PACKAGE("package",   fTokenTag.KWRD),
	T_PRIVATE("private",   fTokenTag.KWRD),
	T_PROTECTED("protected",   fTokenTag.KWRD),
	T_RETURN("return",   fTokenTag.KWRD),
	T_SEALED("sealed",   fTokenTag.KWRD),
	T_SUPER("super",   fTokenTag.KWRD),
	T_THIS("this",   fTokenTag.KWRD),
	T_THROW("throw",   fTokenTag.KWRD),
	T_TRAIT("trait",   fTokenTag.KWRD),
	T_TRUE("true",   fTokenTag.KWRD),
	T_TRY("try",   fTokenTag.KWRD),
	T_TYPE("type",   fTokenTag.KWRD),
	T_VAL("val",   fTokenTag.KWRD),
	T_VAR("var",   fTokenTag.KWRD),
	T_WHILE("while",   fTokenTag.KWRD),
	T_WITH("with",   fTokenTag.KWRD),
	T_YIELD("yield",   fTokenTag.KWRD),

	T_EQUAL("==",   fTokenTag.OPERATOR),
	T_NOT_EQUAL("!=",   fTokenTag.OPERATOR),
	T_LOGICAL_AND("&&",   fTokenTag.OPERATOR),
	T_LOGICAL_OR("||",   fTokenTag.OPERATOR),
	T_ASSIGN("=",   fTokenTag.OPERATOR),
	T_EXCLAMATION("!",   fTokenTag.OPERATOR),
	T_POUND("#",   fTokenTag.OPERATOR),
	T_PERCENT("%",   fTokenTag.OPERATOR),
	T_AMPERSAND("&",   fTokenTag.OPERATOR),
	T_AT("@",   fTokenTag.OPERATOR),
	T_PIPE("|",   fTokenTag.OPERATOR),
	T_COLON(":",   fTokenTag.OPERATOR),
	T_TILDE("~",   fTokenTag.OPERATOR),
	T_PLUS("+",   fTokenTag.OPERATOR),
	T_MINUS("-",   fTokenTag.OPERATOR),
	T_STAR("*",   fTokenTag.OPERATOR),
	T_FORWARD_SLASH("/",   fTokenTag.OPERATOR),
	T_LT("<",   fTokenTag.OPERATOR),
	T_LTE("<=",   fTokenTag.OPERATOR),
	T_GT(">",   fTokenTag.OPERATOR),
	T_GTE(">=",   fTokenTag.OPERATOR),
	T_CARET("^",   fTokenTag.OPERATOR),
	T_QUESTION("?",   fTokenTag.OPERATOR),
	T_UNDERSCORE("_",   fTokenTag.OPERATOR),
	T_FAT_ARROW("=>",   fTokenTag.OPERATOR),
	T_IN("<-",   fTokenTag.OPERATOR),
	T_DOT(".",   fTokenTag.OPERATOR),
	T_COMMA(",",   fTokenTag.OPERATOR),
	T_SEMICOLON(";",   fTokenTag.OPERATOR),
	T_UPPER_BOUND("<:",   fTokenTag.OPERATOR),
	T_LOWER_BOUND(">:",   fTokenTag.OPERATOR),
	T_LCURL("{",   fTokenTag.OPERATOR),
	T_RCURL("}",   fTokenTag.OPERATOR),
	T_LPAREN("(",   fTokenTag.OPERATOR),
	T_RPAREN(")",   fTokenTag.OPERATOR),
	T_LBRACKET("[",   fTokenTag.OPERATOR),
	T_RBRACKET("]",   fTokenTag.OPERATOR),
	T_CONTEXT_BOUND("<%", fTokenTag.OPERATOR);

	fTokenKind(fTokenTag tag) {
		this(null, tag);
	}

	fTokenKind(String name, fTokenTag tag) {
		this.tkName = name;
		this.tag = tag;
	}

	public final String tkName;
	public final fTokenTag tag;
}
