package com.flint.compiler.frontend.parse.lex.token;

import com.flint.compiler.frontend.lang.MethNameCheck;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.HashMap;
import java.util.Map;

public class fLangOperatorMap {
	private static final Map<String, fLangOperatorKind> map = new HashMap<>();

	static {
		for (fLangOperatorKind token : fLangOperatorKind.values()) {
			if(!token.opname.startsWith("@")) map.put(token.opname, token);
		}
	}


	public static fLangOperatorKind getOperatorKind(fToken token) {
		switch (token.kind) {
			case T_IF:
				return fLangOperatorKind.O_IF;
			case T_ELSE:
				return fLangOperatorKind.O_ELSE;
			case T_COMMA:
				return fLangOperatorKind.O_COMMA;
			case T_FAT_ARROW:
				return fLangOperatorKind.O_FAT_ARROW;
			case T_SEMICOLON:
				return fLangOperatorKind.O_SEMICOLON;
			case T_DOT:
				return fLangOperatorKind.O_DOT;
			case T_MATCH:
				return fLangOperatorKind.O_MATCH;
			case T_ID:
				fLangOperatorKind op = map.get(token.getName());
				if(op != null) return op;
				if(MethNameCheck.isMNameValid(token.getName())) {
					if(token.getName().endsWith(":")) {
						return fLangOperatorKind.O_ID_SMBLC_RIGHT_ASSC;
					}
					return fLangOperatorKind.O_ID_SMBLC_LEFT_ASSC;
				}
				throw new RuntimeException("Invalid Symbolic Method Name: "+token.getName());
			default:
				throw new AssertionError("Token is not an operator");
		}
	}
}
