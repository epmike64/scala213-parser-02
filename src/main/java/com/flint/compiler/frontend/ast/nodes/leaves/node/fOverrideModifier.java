package com.flint.compiler.frontend.ast.nodes.leaves.node;

public class fOverrideModifier extends fModifier {

	public fOverrideModifier(){
		super(modTy.OVERRIDE);
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}


	@Override
	public String toString() {
		return "fOverrideModifier{}";
	}
}
