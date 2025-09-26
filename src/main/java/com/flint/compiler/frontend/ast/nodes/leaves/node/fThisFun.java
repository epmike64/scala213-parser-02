package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

import java.util.Optional;

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

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fParamClauses getParamClauses() {
		return paramClauses;
	}

	public fConstrBlock getConstrBlock() {
		return constructorBlock;
	}

	@Override
	public String toString() {
		return "fThisFun{" +
				"mods=" + getMods() +
				", paramClauses=" + paramClauses +
				", constructorBlock=" + constructorBlock +
				'}';
	}
}
