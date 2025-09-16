package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fModifiers extends AstOperandNod {

	private fAccessModifier accessModifier;
	private fOverrideModifier overrideModifier;
	private fLocalModifier localModifier;

	public void setAccessModifier(fAccessModifier accessModifier) {
		assert this.accessModifier == null && accessModifier != null;
		this.accessModifier = accessModifier;
	}
	public void setOverrideModifier(fOverrideModifier overrideModifier) {
		assert this.overrideModifier == null && overrideModifier != null;
		this.overrideModifier = overrideModifier;
	}
	public void setLocalModifier(fLocalModifier localModifier) {
		assert this.localModifier == null && localModifier != null;
		this.localModifier = localModifier;
	}
	public fAccessModifier getAccessModifier() {
		return accessModifier;
	}
	public fOverrideModifier getOverrideModifier() {
		return overrideModifier;
	}
	public fLocalModifier getLocalModifier() {
		return localModifier;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
