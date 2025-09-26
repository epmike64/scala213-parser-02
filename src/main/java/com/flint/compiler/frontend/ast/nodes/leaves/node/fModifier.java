package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.fModifierTypes;

public abstract class fModifier extends AstOperandNod {
	final fModifierTypes modType;

	protected fModifier(fModifierTypes type) {
		this.modType = type;
	}

	public fModifierTypes getModifierType() {
		return modType;
	}

	@Override
	public String toString() {
		return "fModifier{" +
				"modType=" + modType +
				'}';
	}

}
