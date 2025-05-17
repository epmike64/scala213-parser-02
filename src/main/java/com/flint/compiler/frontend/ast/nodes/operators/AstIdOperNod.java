package com.flint.compiler.frontend.ast.nodes.operators;

import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;

public class AstIdOperNod extends AstOperatorNod {

	@Override
	public AstNodKind astNKind() {
		return AstNodKind.AST_ID_OPER;
	}
}
