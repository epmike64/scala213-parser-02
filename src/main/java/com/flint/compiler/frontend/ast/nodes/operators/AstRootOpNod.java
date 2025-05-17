package com.flint.compiler.frontend.ast.nodes.operators;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;

public class AstRootOpNod extends AstOperatorNod {

	@Override
	public void setAstParentN(AstOperatorNod astParentN) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public void setAstLeftN(AstNod astLeftN) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public AstNodKind astNKind() {
		return AstNodKind.AST_ROOT;
	}
}
