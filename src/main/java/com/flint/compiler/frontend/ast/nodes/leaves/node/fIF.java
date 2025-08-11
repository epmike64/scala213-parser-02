package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fIF extends AstOperandNod {
	private final AstOperandNod condition;
	private AstOperandNod ifBody, elseBody;
	public fIF(AstOperandNod condition) {
		this.condition = condition;
	}
	public void setIfBody(AstOperandNod ifBody) {
		this.ifBody = ifBody;
	}
	public void setElseBody(AstOperandNod elseBody) {
		this.elseBody = elseBody;
	}
}
