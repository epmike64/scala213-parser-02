package com.flint.compiler.frontend.ast.nodes.leaves.node;


import com.flint.compiler.frontend.parse.lex.token.fModifierTypes;

public class fLocalModifier extends fModifier {


	public fLocalModifier(fModifierTypes modType) {
		super(modType);
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
