package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fClassTemplate extends fTemplateBody {
	private final fClassParents parents;

	public fClassTemplate(AstProdSubTreeN templateBody, fClassParents parents) {
		super(templateBody);
		this.parents = parents;
	}
}
