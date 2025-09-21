package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fCaseClause {
	private final AstProdSubTreeN pattern;
	private AstOperandNod guard, block;
	public fCaseClause(AstProdSubTreeN pattern) {
		this.pattern = pattern;
	}
	public void setGuard(AstOperandNod guard) { this.guard = guard;}
	public void setBlock(AstOperandNod block) {this.block = block;}

	@Override
	public String toString() {
		return "fCaseClause{" +
				"pattern=" + pattern +
				", guard=" + guard +
				", block=" + block +
				'}';
	}
}