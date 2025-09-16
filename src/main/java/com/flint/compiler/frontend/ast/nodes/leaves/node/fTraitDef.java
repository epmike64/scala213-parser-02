package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;

public class fTraitDef extends AstOperandNod {
	private final fNamedToken name;
	protected List<fVariantTypeParam> typeParams;
	protected fTemplateBody extendsTemplate;

	public fTraitDef(fNamedToken name) {
		this.name = name;
	}

	public void setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	public void setExtendsTemplate(fTemplateBody extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

}
