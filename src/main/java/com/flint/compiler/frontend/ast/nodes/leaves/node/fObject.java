package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fObject extends AstOperandNod  {

	private boolean isCaseClass;
	private final fNamedToken name;
	private fTemplateBody extendsTemplate;

	public fObject(fNamedToken name, boolean isCaseClass) {
		this.name = name;
		this.isCaseClass = isCaseClass;
	}

	public void setExtendsTemplate(fTemplateBody extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}
}
