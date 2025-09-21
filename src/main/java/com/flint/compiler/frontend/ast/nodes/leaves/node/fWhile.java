package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
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

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public AstProdSubTreeN getCondition() {
		return condition;
	}
	public AstProdSubTreeN getBody() {
		return body;
	}
	@Override
	public String toString() {
		return "fWhile{" +
				"condition=" + condition +
				", body=" + body +
				'}';
	}
}
