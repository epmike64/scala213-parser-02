package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;


public class fBlock extends AstOperandNod {
	private final List<AstNod> statements = new ArrayList<>();

	public void addStmt(AstNod st) {
		statements.add(st);
	}

	public List<AstNod> getStmts() {
		return statements;
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "fBlock{" +
				"statements=" + statements +
				'}';
	}
}
