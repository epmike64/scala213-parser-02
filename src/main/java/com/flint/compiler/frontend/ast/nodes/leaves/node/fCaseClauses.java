package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fCaseClauses extends AstOperandNod {
	private final List<fCaseClause> caseClauses;

	public fCaseClauses(List<fCaseClause> caseClauses) {
		assert caseClauses != null && !caseClauses.isEmpty();
		this.caseClauses = caseClauses;
	}

	@Override
	public void accept(AstNodVisitor v) { v.visit(this);}

	@Override
	public String toString() {
		return "fCaseClauses{" +
				"caseClauses=" + caseClauses +
				'}';
	}
}
