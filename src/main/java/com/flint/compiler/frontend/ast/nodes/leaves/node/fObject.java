package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fObject extends AstOperandNod  {

	private boolean isCaseClass;
	private final NamedToken name;
	private fTemplateBody extendsTemplate;

	public fObject(NamedToken name, boolean isCaseClass) {
		this.name = name;
		this.isCaseClass = isCaseClass;
	}

	public void setExtendsTemplate(fTemplateBody extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}
}
