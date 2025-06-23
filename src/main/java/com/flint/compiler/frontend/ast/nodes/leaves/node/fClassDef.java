package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fClassDef extends AstOperandNod {
	private boolean isCaseClass;
	private final NamedToken name;
	private List<fVariantTypeParam> typeParams;
	private fClassParamClauses classParamClauses;

	public fClassDef(NamedToken name, boolean isCaseClass) {
		this.name = name;
		this.isCaseClass = isCaseClass;
	}

	public void  setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	public void setClassParamClauses(fClassParamClauses classParamClauses) {
		this.classParamClauses = classParamClauses;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}
}
