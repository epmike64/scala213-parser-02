package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;
import java.util.Optional;

public class fGenerator extends AstOperandNod {
	private final AstProdSubTreeN casePattern1;
	private final boolean isCase;
	private Optional<List<AstProdSubTreeN>> guards = Optional.empty();
	private AstProdSubTreeN inExpr;
	private Optional<List<AstProdSubTreeN>> endingPattern1s, endingExprs;

	public fGenerator(AstProdSubTreeN pattern1, boolean isCase) {
		this.casePattern1 = pattern1;
		this.isCase = isCase;
	}
	public void setInExpr(AstProdSubTreeN inExpr) {
		this.inExpr = inExpr;
	}
	public void addGuard(AstProdSubTreeN guard) {
		assert  guard != null : "guard cannot be null";
		if(!guards.isPresent()) {
			guards = Optional.of(new java.util.ArrayList<>());
		}
		this.guards.get().add(guard);
	}

	public void addEndingPattern1(AstProdSubTreeN ep1) {
		assert  ep1 != null : "endingPattern1 cannot be null";
		if(!endingPattern1s.isPresent()) {
			endingPattern1s = Optional.of(new java.util.ArrayList<>());
		}
		this.endingPattern1s.get().add(ep1);
	}
	public void addEndingExpr(AstProdSubTreeN ee) {
		assert ee != null : "endingExpr cannot be null";
		if(!endingExprs.isPresent()) {
			endingExprs = Optional.of(new java.util.ArrayList<>());
		}
		this.endingExprs.get().add(ee);
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

	public Optional<List<AstProdSubTreeN>> getGuards() {
		return guards;
	}

	public Optional<List<AstProdSubTreeN>> getEndingPattern1s() {
		return endingPattern1s;
	}

	public AstProdSubTreeN getInExpr() {
		return inExpr;
	}

	public Optional<List<AstProdSubTreeN>> getEndingExprs() {
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
