package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;



public class fClassConstr extends AstOperandNod {
	private final fParamType fParamType;
	private AstProdSubTreeN args;

	public fClassConstr(fParamType fParamType) {
		this.fParamType = fParamType;
	}

	public void setArgExprs(AstProdSubTreeN as) {
		this.args = as;
	}

	public fParamType getParamType() {
		return fParamType;
	}
	public AstProdSubTreeN getArgs() {
		return args;
	}


	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fClassConstr{" +
				"fParamType=" + fParamType +
				", args=" + args +
				'}';
	}
}
