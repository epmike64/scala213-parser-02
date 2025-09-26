package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fModifiers extends AstOperandNod {

	private fAccessModifier accessModifier;
	private fOverrideModifier overrideModifier;
	private fLocalModifier localModifier;

	public void setAccessModifier(fAccessModifier m) {
		this.accessModifier = m;
	}

	public void setOverrideModifier(fOverrideModifier m) {
		this.overrideModifier = m;
	}

	public void setLocalModifier(fLocalModifier m) {
		this.localModifier = m;
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

	@Override
	public String toString() {
		return "fModifiers{" +
				"accessModifier=" + accessModifier +
				", overrideModifier=" + overrideModifier +
				", localModifier=" + localModifier +
				'}';
	}
}
