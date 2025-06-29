package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

public class fId extends AstOperandNod {
	private NamedToken id;
	public fId(NamedToken id) {
		this.id = id;
	}
}
