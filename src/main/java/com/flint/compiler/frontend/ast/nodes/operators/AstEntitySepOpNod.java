package com.flint.compiler.frontend.ast.nodes.operators;

import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;

import static com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind.AST_ENTITY_SEP;

public class AstEntitySepOpNod extends AstOperatorNod {
	@Override
	public AstNodKind astNKind() {
		return AST_ENTITY_SEP;
	}
}
