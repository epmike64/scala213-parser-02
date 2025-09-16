package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

public class fValueDef extends fValue  {


	public fValueDef(fModifiers modifiers) {
		super(modifiers);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
