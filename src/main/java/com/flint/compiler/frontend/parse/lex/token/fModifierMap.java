package com.flint.compiler.frontend.parse.lex.token;

import com.flint.compiler.frontend.parse.lex.token.fModifier.fAccessQualifier;
import com.flint.compiler.frontend.parse.lex.token.fModifier.fModifierKind;

import java.util.HashMap;
import java.util.Map;

public class fModifierMap {

	private static final Map<String, fModifierKind> modKMap = new HashMap<>();
	private static final Map<String, fAccessQualifier> accQmap = new HashMap<>();

	static {
		for (fModifierKind t : fModifierKind.values()) {
			modKMap.put(t.modname, t);
		}
		for (fAccessQualifier t : fAccessQualifier.values()) {
			accQmap.put(t.name(), t);
		}
	}

	public static fModifierKind getModifierKind(String name) {
		return modKMap.getOrDefault(name, null);
	}

	public static fAccessQualifier getAccessQualifier(String name) {
		return accQmap.getOrDefault(name, null);
	}
}
