package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;
import java.util.Optional;

public class fBlock extends AstOperandNod {
	private Optional<List<AstNod>> statements = Optional.empty();
	public void addStmt(AstNod st) {
		if(st == null) {throw new IllegalArgumentException("Statement cannot be null");}
		if(!statements.isPresent()) {statements = Optional.of(new java.util.ArrayList<>());}
		statements.get().add(st);
	}

	public Optional<List<AstNod>> getStmts() {
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
