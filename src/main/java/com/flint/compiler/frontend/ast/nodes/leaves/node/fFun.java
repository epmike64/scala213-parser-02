package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public abstract class fFun extends AstOperandNod {
	private final fModifiers mods;
	public fFun(fModifiers mods) {
		this.mods = mods;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
