package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.utils.Ast;

public class AstProdSubTreeN extends AstOperandNod {
	private final GrmPrd gp;
	private final AstRootOpNod rootOpNod;

	public AstProdSubTreeN(GrmPrd gp, AstRootOpNod rootOp) {
		this.gp = gp;
		this.rootOpNod = rootOp;
	}
	public AstProdSubTreeN(GrmPrd gp, Ast a) {
		this(gp, a.rootOp);
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}

	public AstRootOpNod getRootOpNod() {
		return rootOpNod;
	}

	public GrmPrd getGp() {
		return gp;
	}

	@Override
	public String toString() {
		return "AstProdSubTreeN{" +
				"gp=" + gp +
				'}';
	}
}
