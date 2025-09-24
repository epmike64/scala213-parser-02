package com.flint.compiler.frontend.parse.lex.token;
import java.util.HashMap;
import java.util.Map;

public class fTokenMap {
	private static final Map<String, fTokenKind> map = new HashMap<>();

	static {
		for (fTokenKind token : fTokenKind.values()) {
			if(token.tKndName != null) map.put(token.tKndName, token);
		}
	}

	public static fTokenKind lookupKind(String name) {
		return map.getOrDefault(name, fTokenKind.T_ID);
	}
}