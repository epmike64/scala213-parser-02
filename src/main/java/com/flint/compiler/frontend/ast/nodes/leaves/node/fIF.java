package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fIF extends AstOperandNod {
	private final AstOperandNod condition;
	private AstProdSubTreeN ifBody, elseBody;
	public fIF(AstOperandNod condition) {
		this.condition = condition;
	}

	public void setIfBody(AstProdSubTreeN ifBody) {
		this.ifBody = ifBody;
	}
	public  void setElseBody(AstProdSubTreeN elseBody) {
		this.elseBody = elseBody;
	}
}
