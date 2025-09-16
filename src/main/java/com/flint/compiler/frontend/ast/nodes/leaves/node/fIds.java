package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;

public class fIds extends AstOperandNod {
	private final List<fNamedToken> ids;
	public fIds(List<fNamedToken> ids) {
		this.ids = ids;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
