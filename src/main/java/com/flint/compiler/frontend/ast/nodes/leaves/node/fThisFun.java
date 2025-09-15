package com.flint.compiler.frontend.ast.nodes.leaves.node;

public class fThisFun extends fFun {

	private fParamClauses paramClauses;
	private fConstrBlock constructorBlock;

	public fThisFun(fModifiers mods) {
		super(mods);
	}

	public void setParamClauses(fParamClauses paramClauses) {
		this.paramClauses = paramClauses;
	}
	public void setConstrBlock(fConstrBlock constructorBlock) {
		this.constructorBlock = constructorBlock;
	}
}
