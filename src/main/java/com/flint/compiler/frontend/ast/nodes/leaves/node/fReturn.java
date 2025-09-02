package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fReturn extends AstOperandNod {
	private AstProdSubTreeN expr;
	public void setExpr(AstProdSubTreeN expr) {
		this.expr = expr;
	}
}
