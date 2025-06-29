package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fConstrBlock extends AstOperandNod {
	private AstProdSubTreeN argExprs;
	private List<AstProdSubTreeN> blockStatements;

	public void setArgExprs(AstProdSubTreeN argExprs) {
		this.argExprs = argExprs;
	}

	public void addBlockStat(AstProdSubTreeN statement) {
		if (blockStatements == null) {
			blockStatements = new java.util.ArrayList<>();
		}
		blockStatements.add(statement);
	}
}
