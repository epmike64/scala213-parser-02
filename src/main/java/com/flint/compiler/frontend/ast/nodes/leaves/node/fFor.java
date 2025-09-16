package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fFor extends AstOperandNod {
	private final List<fGenerator> generators;
	private boolean isYield = false;
	private AstProdSubTreeN expr;
	public fFor(List<fGenerator> generators) {
		this.generators = generators;
	}
	public void setYield(boolean yield) {
		isYield = yield;
	}
	public void setExpr(AstProdSubTreeN expr) {
		this.expr = expr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
