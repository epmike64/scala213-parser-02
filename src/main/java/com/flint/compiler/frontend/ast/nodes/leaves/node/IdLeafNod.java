package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

public class IdLeafNod extends AstLeafNod{
	final NamedToken name;
	public IdLeafNod(NamedToken name) {
		this.name = name;
	}

	@Override
	public AstNodKind astNKind() {
		return AstNodKind.AST_ID_LEAF;
	}
}
