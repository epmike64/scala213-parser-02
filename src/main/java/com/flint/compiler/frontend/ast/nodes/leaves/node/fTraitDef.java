package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.List;


public class fTraitDef extends AstOperandNod {
	private final fNameValToken name;
	private final fModifiers modifiers;
	private List<fVariantTypeParam> typeParams ;
	private AstOperandNod extendsTemplate ;

	public fTraitDef(fNameValToken name, fModifiers modifiers) {
		this.name = name;
		this.modifiers = modifiers;
	}

	public void setTypeParams(List<fVariantTypeParam> tps) {
		this.typeParams = tps;
	}

	public void setExtendsTemplate(AstOperandNod et) {
		this.extendsTemplate = et;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNameValToken getName() {
		return name;
	}
	public List<fVariantTypeParam> getTypeParams() {
		return typeParams;
	}

	public AstOperandNod getExtendsTemplate() {
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
