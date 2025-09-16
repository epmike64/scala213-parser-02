package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fConstrBlock extends AstOperandNod {
	private AstProdSubTreeN argExprs;
	private List<AstNod> blockStatements;

	public void setArgExprs(AstProdSubTreeN argExprs) {
		this.argExprs = argExprs;
	}

	public void addBlockStat(AstNod statement) {
		if (blockStatements == null) {
			blockStatements = new java.util.ArrayList<>();
		}
		blockStatements.add(statement);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
