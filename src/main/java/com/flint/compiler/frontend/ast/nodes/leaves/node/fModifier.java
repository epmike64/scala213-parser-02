package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public abstract class fModifier extends AstOperandNod {
	final modTy modType;
	protected fModifier(modTy type) {
		this.modType = type;
	}
	public modTy getModifierType() {
		return modType;
	}

	@Override
	public String toString() {
		return "fModifier{" +
				"modType=" + modType +
				'}';
	}

}
