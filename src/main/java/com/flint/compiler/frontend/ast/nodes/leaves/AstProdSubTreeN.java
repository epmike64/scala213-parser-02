package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;

public class AstProdSubTreeN extends AstSubTreeNod {
	final GrmPrd gp;

	public AstProdSubTreeN(GrmPrd p, AstRootOpNod rootOp) {
		super(rootOp);
		this.gp = p;
	}
}
