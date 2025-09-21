package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fReturn extends AstOperandNod {
	private AstProdSubTreeN expr;
	public void setExpr(AstProdSubTreeN expr) {
		this.expr = expr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public AstProdSubTreeN getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "fReturn{" +
				"expr=" + expr +
				'}';
	}
}
