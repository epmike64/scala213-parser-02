package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fTry extends AstOperandNod {
	private final AstOperandNod tryBlock;
	private AstOperandNod catchBlock, finallyBlock;
	public fTry(AstOperandNod tryBlock) {
		this.tryBlock = tryBlock;
	}
	public void setCatchBlock(AstOperandNod catchBlock) {
		this.catchBlock = catchBlock;
	}
	public void setFinallyBlock(AstOperandNod finallyBlock) {
		this.finallyBlock = finallyBlock;
	}
}
