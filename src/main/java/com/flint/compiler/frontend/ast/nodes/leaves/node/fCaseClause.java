package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.Optional;

public class fCaseClause extends AstOperandNod {
	private final AstProdSubTreeN pattern;
	private Optional<AstOperandNod> guard;
	private AstOperandNod block;
	public fCaseClause(AstProdSubTreeN pattern) {
		this.pattern = pattern;
	}
	public void setGuard(AstOperandNod g) {
		assert g != null : "guard cannot be null";
		if(guard.isPresent()) throw new IllegalStateException("guard already set");
		this.guard = Optional.of(g);
	}

	public void setBlock(AstOperandNod b) {
		assert b != null : "block cannot be null";
		this.block = b;
	}
	public AstProdSubTreeN getPattern() {
		return pattern;
	}
	public Optional<AstOperandNod> getGuard() {
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