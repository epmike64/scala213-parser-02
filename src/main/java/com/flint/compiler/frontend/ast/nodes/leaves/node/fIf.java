package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

import java.util.Optional;

public class fIf extends AstOperandNod {
	private final AstOperandNod condExpr;
	private AstProdSubTreeN ifBody;
	private Optional<AstProdSubTreeN> elseBody = Optional.empty();
	public fIf(AstOperandNod condition) {
		this.condExpr = condition;
	}

	public AstOperandNod getCondExpr() {
		return condExpr;
	}

	public AstProdSubTreeN getIfBody() {
		return ifBody;
	}



	public void setIfBody(AstProdSubTreeN ifBody) {
		this.ifBody = ifBody;
	}
	public  void setElseBody(AstProdSubTreeN elseBody) {
		this.elseBody = Optional.of(elseBody);
	}

	public Optional<AstProdSubTreeN> getElseBody() {
		return elseBody;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
