package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fClassConstructor extends AstOperandNod {
	private fParamType fParamType;
	private AstProdSubTreeN args;
	public fClassConstructor(fParamType fParamType) {
		this.fParamType = fParamType;
	}
	public void setArgExprs(AstProdSubTreeN args) {
		this.args = args;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
