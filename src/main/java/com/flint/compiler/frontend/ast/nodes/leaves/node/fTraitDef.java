package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

import java.util.List;

public class fTraitDef extends AstOperandNod {
	private final NamedToken name;
	protected List<fVariantTypeParam> typeParams;
	protected fTemplateBody extendsTemplate;

	public fTraitDef(NamedToken name) {
		this.name = name;
	}

	public void setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	public void setExtendsTemplate(fTemplateBody extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}

}
