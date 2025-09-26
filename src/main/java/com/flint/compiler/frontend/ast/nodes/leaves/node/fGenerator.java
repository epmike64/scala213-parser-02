package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class fGenerator extends AstOperandNod {
	private final AstProdSubTreeN casePattern1;
	private final boolean isCase;
	private final List<AstProdSubTreeN> guards = new ArrayList<>();
	private AstProdSubTreeN inExpr;
	private final List<AstProdSubTreeN> endingPattern1s = new ArrayList<>();
	private final List<AstProdSubTreeN> endingExprs = new ArrayList<>();

	public fGenerator(AstProdSubTreeN pattern1, boolean isCase) {
		this.casePattern1 = pattern1;
		this.isCase = isCase;
	}
	public void setInExpr(AstProdSubTreeN inExpr) {
		this.inExpr = inExpr;
	}
	public void addGuard(AstProdSubTreeN guard) {
		this.guards.add(guard);
	}

	public void addEndingPattern1(AstProdSubTreeN ep1) {
		this.endingPattern1s.add(ep1);
	}
	public void addEndingExpr(AstProdSubTreeN ee) {
		this.endingExprs.add(ee);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public AstProdSubTreeN getCasePattern1() {
		return casePattern1;
	}

	public boolean isCase() {
		return isCase;
	}

	public List<AstProdSubTreeN> getGuards() {
		return guards;
	}

	public List<AstProdSubTreeN> getEndingPattern1s() {
		return endingPattern1s;
	}

	public AstProdSubTreeN getInExpr() {
		return inExpr;
	}

	public List<AstProdSubTreeN> getEndingExprs() {
		return endingExprs;
	}
	@Override
	public String toString() {
		return "fGenerator{" +
				"casePattern1=" + casePattern1 +
				", isCase=" + isCase +
				", guards=" + guards +
				", inExpr=" + inExpr +
				", endingPattern1s=" + endingPattern1s +
				", endingExprs=" + endingExprs +
				'}';
	}
}
