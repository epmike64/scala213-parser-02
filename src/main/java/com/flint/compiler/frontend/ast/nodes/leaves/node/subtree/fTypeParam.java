package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

public class fTypeParam {
	private NamedToken name;
	public fTypeParam(NamedToken name) {
		this.name = name;
	}
}
