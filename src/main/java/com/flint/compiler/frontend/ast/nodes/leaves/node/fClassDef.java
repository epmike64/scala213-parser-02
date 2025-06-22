package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fClassDef extends AstOperandNod {
	private boolean isCaseClass;
	private final NamedToken name;
	public fClassDef(NamedToken name, boolean isCaseClass) {
		this.name = name;
		this.isCaseClass = isCaseClass;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}
}
