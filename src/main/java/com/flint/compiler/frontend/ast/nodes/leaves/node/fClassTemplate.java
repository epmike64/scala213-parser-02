package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

public class fClassTemplate extends fTemplateBody {
	private final fClassParents parents;
	private final boolean isTrait;
	public fClassTemplate(fTemplateBody templateBody, fClassParents parents, boolean isTrait) {
		super(templateBody.getTemplateBody());
		this.parents = parents;
		this.isTrait = isTrait;
	}
	public fClassTemplate(fClassParents parents, boolean isTrait) {
		super(null);
		this.parents = parents;
		this.isTrait = isTrait;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
