package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fLiteral extends AstOperandNod {
	private final fToken token;
	public fLiteral(fToken token) {
		this.token = token;
	}
}
