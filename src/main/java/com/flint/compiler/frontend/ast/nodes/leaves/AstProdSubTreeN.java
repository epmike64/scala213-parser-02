package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.utils.Ast;

public class AstProdSubTreeN extends AstSubTreeNod {
	final GrmPrd gProd;

	public AstProdSubTreeN(GrmPrd gProd, Ast ast) {
		super(ast);
		this.gProd = gProd;
	}
}
