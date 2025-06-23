package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fClassDef extends AstOperandNod {
	private final boolean trait;
	private final boolean isCaseClass;
	private final NamedToken name;
	private List<fVariantTypeParam> typeParams;
	private fClassParamClauses classParamClauses;
	private fClassTemplate extendsTemplate;

	public fClassDef(boolean isTrait, NamedToken name, boolean isCaseClass) {
		this.trait = isTrait;
		this.name = name;
		this.isCaseClass = isCaseClass;
	}

	public void  setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	public void setClassParamClauses(fClassParamClauses classParamClauses) {
		this.classParamClauses = classParamClauses;
	}


	public void setExtendsTemplate(fClassTemplate extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}
}
