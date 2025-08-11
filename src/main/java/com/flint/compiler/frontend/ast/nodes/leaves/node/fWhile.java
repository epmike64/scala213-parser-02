package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fWhile extends AstOperandNod {
	private final AstProdSubTreeN condition;
	private AstProdSubTreeN body;
	public fWhile(AstProdSubTreeN condition) {
		this.condition = condition;
	}
	public void setWhileBody(AstProdSubTreeN body) {
		this.body = body;
	}
}
