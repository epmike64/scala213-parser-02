package com.flint.compiler.frontend.ast.nodes.leaves.node;

public class fClassTemplate extends fTemplateBody {
	private final fClassParents parents;

	public fClassTemplate(fTemplateBody templateBody, fClassParents parents) {
		super(templateBody.getTemplateBody());
		this.parents = parents;
	}
}
