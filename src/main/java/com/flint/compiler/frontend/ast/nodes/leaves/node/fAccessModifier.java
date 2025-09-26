package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.parse.lex.token.fModifierTypes;


public class fAccessModifier extends fModifier {

	private fAccessQualifier qualifier;

	public fAccessModifier(fModifierTypes kind) {
		super(kind);
	}

	public void setQualifier(fAccessQualifier q) {
		this.qualifier = q;
	}

	public fAccessQualifier getQualifier() {
		return qualifier;
	}


	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
