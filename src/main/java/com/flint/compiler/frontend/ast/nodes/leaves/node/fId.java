package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

public class fId extends AstOperandNod {
	private fNamedToken id;
	public fId(fNamedToken id) {
		this.id = id;
	}
}
