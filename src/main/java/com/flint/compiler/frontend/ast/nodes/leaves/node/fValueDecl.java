package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;


public class fValueDecl extends fValue{
	public fValueDecl(fModifiers modifiers) {
		super(modifiers);
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fValueDecl{" +
				"modifiers=" + getModifiers() +
				", id=" + getNames() +
				", type=" + getType() +
				", expr=" + getAssignExpr() +
				'}';
	}
}
