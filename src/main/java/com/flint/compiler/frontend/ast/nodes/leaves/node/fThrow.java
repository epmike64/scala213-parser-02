package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fThrow extends AstOperandNod {
	private final AstOperandNod expr;
	public fThrow(AstOperandNod expr) {
		this.expr = expr;
	}
}
