package com.flint.compiler.frontend.ast.nodes.leaves.node;

import java.util.Optional;

public class fAccessModifier extends fModifier{

	private Optional<fAccessQualifier> qualifier = Optional.empty();

	public fAccessModifier(modTy kind) {
		super(kind);
	}
	public void setQualifier(fAccessQualifier q) {
		if(qualifier.isPresent()) throw new IllegalStateException("Qualifier already set");
		if(q == null) throw new IllegalArgumentException("Qualifier cannot be null");
		this.qualifier = Optional.of(q);
	}

	public Optional<fAccessQualifier> getQualifier() {
		return qualifier;
	}


	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
