package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.parse.utils.Ast;

public class AstSubTreeNod extends AstOperandNod {
	public final Ast astPtr;
	public AstSubTreeNod(Ast astPtr) {
		this.astPtr = astPtr;
	}

	@Override
	public AstNodKind astNKind() {
		return null;
	}
}
