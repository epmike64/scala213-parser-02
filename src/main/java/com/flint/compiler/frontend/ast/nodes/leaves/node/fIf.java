package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

public class fIf extends AstOperandNod {
	private final AstOperandNod condition;
	private AstProdSubTreeN ifBody, elseBody;
	public fIf(AstOperandNod condition) {
		this.condition = condition;
	}

	public void setIfBody(AstProdSubTreeN ifBody) {
		this.ifBody = ifBody;
	}
	public  void setElseBody(AstProdSubTreeN elseBody) {
		this.elseBody = elseBody;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
