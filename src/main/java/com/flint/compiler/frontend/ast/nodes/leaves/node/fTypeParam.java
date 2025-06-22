package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

import java.util.List;

public class fTypeParam {
	private NamedToken name;
	private List<fVariantTypeParam> variantTypeParams;
	private fType type, lowerBound, upperBound;
	public fTypeParam(NamedToken name) {
		this.name = name;
	}

	public void setVariantTypeParams(List<fVariantTypeParam> variantTypeParams) {
		this.variantTypeParams = variantTypeParams;
	}
	public void setType(fType type) {
		this.type = type;
	}
	public void setLowerBound(fType lowerBound) {
		this.lowerBound = lowerBound;
	}
	public void setUpperBound(fType upperBound) {
		this.upperBound = upperBound;
	}
}
