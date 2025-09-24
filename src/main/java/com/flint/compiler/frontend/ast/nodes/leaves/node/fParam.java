package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

public class fParam extends AstOperandNod {
	private final fNameValToken name;
	private fParamType paramType;
	private AstProdSubTreeN defaultValue;

	public fParam(fNameValToken name) {
		this.name = name;
	}
	public void setParamType(fParamType typeParam) {
		this.paramType = typeParam;
	}
	public void setDefaultValue(AstProdSubTreeN defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNameValToken getName() {
		return name;
	}
	public fParamType getParamType() {
		return paramType;
	}
	public AstProdSubTreeN getDefaultValue() {
		return defaultValue;
	}
	@Override
	public String toString() {
		return "fParam{" +
				"name=" + name +
				", paramType=" + paramType +
				", defaultValue=" + defaultValue +
				'}';
	}
}
