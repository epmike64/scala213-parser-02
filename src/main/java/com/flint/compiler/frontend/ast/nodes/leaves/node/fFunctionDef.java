package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fFunctionDef extends AstOperandNod {
	private final NamedToken name;
	private List<fTypeParam> typeParams;
	public fFunctionDef(NamedToken name) {
		this.name = name;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}

	public void setTypeParams(List<fTypeParam> params){
		this.typeParams = params;
	}
}
