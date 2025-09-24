package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.List;

public class fTypeParam extends AstOperandNod {
	private fNameValToken name;
	private List<fVariantTypeParam> variantTypeParams;
	private fType type, lowerBound, upperBound;
	public fTypeParam(fNameValToken name) {
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

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNameValToken getName() {
		return name;
	}

	public List<fVariantTypeParam> getVariantTypeParams() {
		return variantTypeParams;
	}

	public fType getType() {
		return type;
	}

	public fType getLowerBound() {
		return lowerBound;
	}

	public fType getUpperBound() {
		return upperBound;
	}

	@Override
	public String toString() {
		return "fTypeParam{" +
				"name=" + name +
				", variantTypeParams=" + variantTypeParams +
				", type=" + type +
				", lowerBound=" + lowerBound +
				", upperBound=" + upperBound +
				'}';
	}
}
