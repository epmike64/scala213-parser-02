package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.List;

public class fTemplateBody extends AstOperandNod  {
	private final List<AstProdSubTreeN> stmts = new java.util.ArrayList<>();

	public void addStmt(AstProdSubTreeN s) {
		assert s != null;
		this.stmts.add(s);
	}

	public List<AstProdSubTreeN> getStmts() {
		return stmts;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
