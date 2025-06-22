package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fTraitDef extends AstOperandNod {
	private final NamedToken name;
	public fTraitDef(NamedToken name) {
		this.name = name;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}
}
