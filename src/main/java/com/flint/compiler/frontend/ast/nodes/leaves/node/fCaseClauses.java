package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fCaseClauses extends AstOperandNod {

	public static class fCaseClause {
		private final AstProdSubTreeN pattern;
		private AstProdSubTreeN guard, block;
		public fCaseClause(AstProdSubTreeN pattern) {
			this.pattern = pattern;
		}
		public void setGuard(AstProdSubTreeN guard) {
			this.guard = guard;
		}
		public void setBlock(AstProdSubTreeN block) {
			this.block = block;
		}
	}
	private final List<fCaseClause> caseClauses;
	public fCaseClauses(List<fCaseClause> caseClauses) {
		this.caseClauses = caseClauses;
	}
}
