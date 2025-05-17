package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.lang.grammar.GProd;

public class AstProdSubTreeN extends AstSubTreeNod {
	final GProd gProd;

	public AstProdSubTreeN(GProd gProd, AstRootOpNod astRootOpNode, AstNod lastAdded) {
		super(astRootOpNode, lastAdded);
		this.gProd = gProd;
	}
}
