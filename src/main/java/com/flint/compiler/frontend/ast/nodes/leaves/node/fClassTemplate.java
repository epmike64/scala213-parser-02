package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;



public class fClassTemplate extends fTemplate {
	private final fClassParents parents;

	public fClassTemplate(boolean amExtender, fClassParents parents, fTemplateBody body) {
		super(amExtender, body);
		assert parents != null : "parents cannot be null";
		this.parents = parents;
	}

	public fClassParents getParents() {
		return parents;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fClassTemplate{" +
				"parents=" + parents +
				", amExtender=" + isAmExtender() +
				", templateBody=" + getTemplateBody() +
				'}';
	}
}
