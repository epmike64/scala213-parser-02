package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fFor extends AstOperandNod {
	private final List<fGenerator> generators;
	private boolean isYield = false;
	private AstProdSubTreeN yieldExpr;
	public fFor(List<fGenerator> generators) {
		assert generators != null && !generators.isEmpty() : "generators cannot be null or empty";
		this.generators = generators;
	}
	public void setYield(boolean yield) {
		isYield = yield;
	}
	public void setYieldExpr(AstProdSubTreeN yieldExpr) {
		assert yieldExpr != null : "expr cannot be null";
		this.yieldExpr = yieldExpr;
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
	public AstProdSubTreeN getYieldExpr() {
		return yieldExpr;
	}

	@Override
	public String toString() {
		return "fFor{" +
				"generators=" + generators +
				", isYield=" + isYield +
				", yieldExpr=" + yieldExpr +
				'}';
	}
}
