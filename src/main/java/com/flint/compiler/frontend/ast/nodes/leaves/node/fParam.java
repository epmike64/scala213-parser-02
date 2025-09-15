package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

public class fParam {
	private final fNamedToken name;
	private fParamType paramType;
	private AstProdSubTreeN defaultValue;

	public fParam(fNamedToken name) {
		this.name = name;
	}
	public void setParamType(fParamType typeParam) {
		this.paramType = typeParam;
	}
	public void setDefaultValue(AstProdSubTreeN defaultValue) {
		this.defaultValue = defaultValue;
	}
}
