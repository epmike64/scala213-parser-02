package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;

public class fBlock extends AstOperandNod {
	private final List<AstNod> statements = new java.util.ArrayList<>();
	public void add(AstNod statement) {
		assert statement != null;
		this.statements.add(statement);
	}

	public List<AstNod> getStatements() {
		return statements;
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}
}
