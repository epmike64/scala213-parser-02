package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

import java.util.List;

public class fIds extends AstOperandNod {
	private final List<NamedToken> ids;
	public fIds(List<NamedToken> ids) {
		this.ids = ids;
	}
}
