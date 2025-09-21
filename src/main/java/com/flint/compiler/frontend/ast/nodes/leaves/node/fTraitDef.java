package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.ArrayList;
import java.util.List;

public class fTraitDef extends AstOperandNod {
	private final fNamedToken name;
	private final fModifiers modifiers;
	private final List<fVariantTypeParam> typeParams = new ArrayList<>();
	private fTemplateBody extendsTemplate;

	public fTraitDef(fNamedToken name, fModifiers modifiers) {
		this.name = name;
		this.modifiers = modifiers;
	}

	public void setTypeParams(List<fVariantTypeParam> typeParams) {
		assert typeParams != null && typeParams.size() > 0;
		this.typeParams.addAll(typeParams);
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
	public List<fVariantTypeParam> getTypeParams() {
		return typeParams;
	}
	public fTemplateBody getExtendsTemplate() {
		return extendsTemplate;
	}

	public fModifiers getModifiers() {
		return modifiers;
	}

	@Override
	public String toString() {
		return "fTraitDef{" +
				"name=" + name +
				", modifiers=" + modifiers +
				", typeParams=" + typeParams +
				", extendsTemplate=" + extendsTemplate +
				'}';
	}
}
