package com.flint.compiler.frontend.ast.nodes;

public abstract class AstOperandNod extends AstNod {
	@Override
	public boolean isOperator() {
		return false;
	}

	@Override
	public void setAstLeftN(AstNod astLeftN) {
		throw new UnsupportedOperationException("Cannot set left operand for " + this.getClass().getSimpleName());
	}

	@Override
	public void setAstRightN(AstNod astRightN) {
		throw new UnsupportedOperationException("Cannot set right operand for " + this.getClass().getSimpleName());
	}
}
