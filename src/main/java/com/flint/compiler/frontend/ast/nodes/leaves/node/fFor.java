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
		assert generators != null && !generators.isEmpty() : "generators cannot be null or empty";
		this.generators = generators;
	}
	public void setYield(boolean yield) {
		isYield = yield;
	}
	public void setExpr(AstProdSubTreeN expr) {
		assert expr != null : "expr cannot be null";
		this.expr = expr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<fGenerator> getGenerators() {
		return generators;
	}
	public boolean isYield() {
		return isYield;
	}
	public AstProdSubTreeN getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "fFor{" +
				"generators=" + generators +
				", isYield=" + isYield +
				", expr=" + expr +
				'}';
	}
}
