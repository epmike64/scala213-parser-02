package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fGenerator extends AstOperandNod {
	private final AstProdSubTreeN casePattern1;
	private final boolean isCase;
	private AstProdSubTreeN guard;
	private AstProdSubTreeN inExpr, endingPattern1, endingExpr;
	public fGenerator(AstProdSubTreeN pattern1, boolean isCase) {
		this.casePattern1 = pattern1;
		this.isCase = isCase;
	}
	public void setInitExpr(AstProdSubTreeN initExpr) {
		this.inExpr = initExpr;
	}
	public void setGuard(AstProdSubTreeN guard) {
		this.guard = guard;
	}
	public void setEndingPattern1(AstProdSubTreeN endingPattern1) {
		this.endingPattern1 = endingPattern1;
	}
	public void setEndingExpr(AstProdSubTreeN endingExpr) {
		this.endingExpr = endingExpr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
