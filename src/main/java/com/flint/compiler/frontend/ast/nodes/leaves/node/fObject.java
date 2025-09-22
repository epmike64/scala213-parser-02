package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.Optional;

public class fObject extends AstOperandNod  {

	private final boolean isCaseClass;
	private final fNamedToken name;
	private Optional<AstOperandNod> extendsTemplate = Optional.empty();
	private final Optional<fModifiers> modifiers;

	public fObject(fNamedToken name, boolean isCaseClass, Optional<fModifiers> modifiers) {
		this.name = name;
		this.isCaseClass = isCaseClass;
		this.modifiers = modifiers;
	}

	public void setExtendsTemplate(AstOperandNod et) {
		if( this.extendsTemplate.isPresent()) throw new RuntimeException("Extends template already set");
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

	public Optional<AstOperandNod> getExtendsTemplate() {
		return extendsTemplate;
	}

	public boolean isCaseClass() {
		return isCaseClass;
	}

	public Optional<fModifiers> getModifiers() {
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
