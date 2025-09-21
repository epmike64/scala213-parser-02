package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fUnderscore extends AstOperandNod {
		private final fToken name;
		public fUnderscore(fToken name) {
			this.name = name;
		}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fToken getName() {
		return name;
	}
}
