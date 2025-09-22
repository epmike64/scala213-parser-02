package com.flint.compiler.frontend.ast.nodes.leaves.node;


public class fLocalModifier extends fModifier {


	public fLocalModifier(modTy modType) {
		super(modType);
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
