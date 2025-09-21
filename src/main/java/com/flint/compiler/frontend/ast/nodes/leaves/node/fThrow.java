package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fThrow extends AstOperandNod {
	private final AstOperandNod expr;
	public fThrow(AstOperandNod expr) {
		this.expr = expr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public AstOperandNod getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "fThrow{" +
				"expr=" + expr +
				'}';
	}
}
