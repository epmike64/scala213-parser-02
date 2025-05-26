package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.lang.grammar.GProd;
import com.flint.compiler.frontend.parse.utils.Ast;

public class AstProdSubTreeN extends AstSubTreeNod {
	final GProd gProd;

	public AstProdSubTreeN(GProd gProd, Ast ast) {
		super(ast);
		this.gProd = gProd;
	}
}
