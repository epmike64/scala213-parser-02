package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;
import java.util.Optional;

public class fTraitDef extends AstOperandNod {
	private final fNamedToken name;
	private final Optional<fModifiers> modifiers;
	private Optional<List<fVariantTypeParam>> typeParams = Optional.empty();
	private Optional<fTemplateBody> extendsTemplate = Optional.empty();

	public fTraitDef(fNamedToken name, Optional<fModifiers> modifiers) {
		this.name = name;
		this.modifiers = modifiers;
	}

	public void setTypeParams(List<fVariantTypeParam> tps) {
		if (this.typeParams.isPresent()) throw new RuntimeException("Type parameters already set");
		if(tps == null || tps.size() == 0) throw new RuntimeException("Type parameters cannot be null or empty");
		this.typeParams = Optional.of(tps);
	}

	public void setExtendsTemplate(fTemplateBody et) {
		if (this.extendsTemplate.isPresent()) throw new RuntimeException("Extends template already set");
		if(et == null) throw new RuntimeException("Extends template cannot be null");
		this.extendsTemplate = Optional.of(et);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNamedToken getName() {
		return name;
	}
	public Optional<List<fVariantTypeParam>> getTypeParams() {
		return typeParams;
	}
	public Optional<fTemplateBody> getExtendsTemplate() {
		return extendsTemplate;
	}

	public Optional<fModifiers> getModifiers() {
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
