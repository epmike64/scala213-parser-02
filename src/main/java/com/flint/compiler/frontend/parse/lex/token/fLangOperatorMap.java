package com.flint.compiler.frontend.parse.lex.token;

import com.flint.compiler.frontend.lang.MethNameCheck;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.HashMap;
import java.util.Map;

public class fLangOperatorMap {
	private static final Map<String, fLangOperKind> map = new HashMap<>();

	static {
		for (fLangOperKind token : fLangOperKind.values()) {
			if(!token.opname.startsWith("@")) map.put(token.opname, token);
		}
	}


	public static fLangOperKind getOperatorKind(fToken token) {
		switch (token.kind) {
			case T_IF:
				return fLangOperKind.O_IF;
			case T_ELSE:
				return fLangOperKind.O_ELSE;
			case T_COMMA:
				return fLangOperKind.O_COMMA;
			case T_FAT_ARROW:
				return fLangOperKind.O_FAT_ARROW;
			case T_SEMICOLON:
				return fLangOperKind.O_SEMICOLON;
			case T_DOT:
				return fLangOperKind.O_DOT;
			case T_MATCH:
				return fLangOperKind.O_MATCH;
			case T_ID:
				fLangOperKind op = map.get(token.name());
				if(op != null) return op;
				if(MethNameCheck.isMNameValid(token.name())) {
					if(token.name().endsWith(":")) {
						return fLangOperKind.O_ID_SMBLC_RIGHT_ASSC;
					}
					return fLangOperKind.O_ID_SMBLC_LEFT_ASSC;
				}
				throw new RuntimeException("Invalid Symbolic Method Name: "+token.name());
			default:
				throw new AssertionError("Token is not an operator");
		}
	}
}
