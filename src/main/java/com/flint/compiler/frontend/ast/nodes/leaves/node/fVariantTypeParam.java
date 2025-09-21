package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;


public class fVariantTypeParam extends fTypeParam {
	public enum fVariance {
		INVARIANT, // No variance
		VARIANT,
		NONE
	}

	private fVariance variance = fVariance.NONE;

	public fVariantTypeParam(fNamedToken name) {
		super(name);
	}

	public void setVariance(fVariance variance) {
		this.variance = variance;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fVariantTypeParam{" +
				"variance=" + variance +
				", name=" + getName() +
				'}';
	}
}
