package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;


public class fVariantTypeParam extends fTypeParam {
	public enum fVariance {
		INVARIANT, // No variance
		VARIANT,
		NONE
	}

	private fVariance variance = fVariance.NONE;

	public fVariantTypeParam(NamedToken name) {
		super(name);
	}

	public void setVariance(fVariance variance) {
		this.variance = variance;
	}
}
