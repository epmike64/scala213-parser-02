package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.parse.lex.token.fModifierTypes;

public class fOverrideModifier extends fModifier {

	public fOverrideModifier(){
		super(fModifierTypes.OVERRIDE);
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
