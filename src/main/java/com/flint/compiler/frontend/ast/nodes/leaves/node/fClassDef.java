package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fClassDef extends fTraitDef {
	private final boolean isCaseClass;
	private fClassParamClauses classParamClauses;


	public fClassDef(NamedToken name, boolean isCaseClass) {
		super(name);
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
