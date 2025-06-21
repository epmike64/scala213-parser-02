package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.utils.Ast;

public class AstProdSubTreeN extends AstSubTreeNod {
	final GrmPrd gp;

	public AstProdSubTreeN(GrmPrd gp, AstRootOpNod rootOp) {
		super(rootOp);
		this.gp = gp;
	}
	public AstProdSubTreeN(GrmPrd gp, Ast a) {
		this(gp, a.rootOp);
	}
}
