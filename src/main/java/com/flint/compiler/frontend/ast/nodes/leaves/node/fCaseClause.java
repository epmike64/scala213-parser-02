package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;


public class fCaseClause extends AstOperandNod {
	private final AstProdSubTreeN pattern;
	private AstOperandNod guard;
	private AstOperandNod block;

	public fCaseClause(AstProdSubTreeN pattern) {
		this.pattern = pattern;
	}

	public void setGuard(AstOperandNod g) {
		assert g != null : "guard cannot be null";
		this.guard = g;
	}

	public void setBlock(AstOperandNod b) {
		assert b != null : "block cannot be null";
		this.block = b;
	}

	public AstProdSubTreeN getPattern() {
		return pattern;
	}

	public AstOperandNod getGuard() {
		return guard;
	}

	public AstOperandNod getBlock() {
		return block;
	}

	@Override
	public String toString() {
		return "fCaseClause{" +
				"pattern=" + pattern +
				", guard=" + guard +
				", block=" + block +
				'}';
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}
}