package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fTemplateBody extends AstOperandNod  {
	private final AstProdSubTreeN templateBody;
	public fTemplateBody(AstProdSubTreeN templateBody) {
		this.templateBody = templateBody;
	}
}
