package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fObject extends AstOperandNod  {

	private boolean isCaseClass;
	private final fNamedToken name;
	private fTemplateBody extendsTemplate;
	private final fModifiers modifiers;
	public fObject(fNamedToken name, boolean isCaseClass, fModifiers modifiers) {
		this.name = name;
		this.isCaseClass = isCaseClass;
		this.modifiers = modifiers;
	}

	public void setExtendsTemplate(fTemplateBody extendsTemplate) {
		this.extendsTemplate = extendsTemplate;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNamedToken getName() {
		return name;
	}

	public fTemplateBody getExtendsTemplate() {
		return extendsTemplate;
	}

	public boolean isCaseClass() {
		return isCaseClass;
	}

	public fModifiers getModifiers() {
		return modifiers;
	}

	@Override
	public String toString() {
		return "fObject{" +
				"isCaseClass=" + isCaseClass +
				", name=" + name +
				", extendsTemplate=" + extendsTemplate +
				", modifiers=" + modifiers +
				'}';
	}
}
