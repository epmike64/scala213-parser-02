package com.flint.compiler.frontend.ast.nodes;

public abstract class AstOperatorNod extends AstNod {


	public boolean isOperator() {
		return true;
	}


	@Override
	public void setAstRightN(AstNod astRightN) {
		this.astRightN = astRightN;
		astRightN.setAstParentN(this);
	}


	@Override
	public void setAstLeftN(AstNod astLeftN) {
		this.astLeftN = astLeftN;
		astLeftN.setAstParentN(this);
	}
}
