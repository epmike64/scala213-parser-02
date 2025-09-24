package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

public class fId extends AstOperandNod {
	private fNameValToken id;
	public fId(fNameValToken id) {
		this.id = id;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
