package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

public class fParam {
	private final NamedToken name;
	private fParamType paramType;
	private AstProdSubTreeN defaultValue;

	public fParam(NamedToken name) {
		this.name = name;
	}
	public void setParamType(fParamType typeParam) {
		this.paramType = typeParam;
	}
	public void setDefaultValue(AstProdSubTreeN defaultValue) {
		this.defaultValue = defaultValue;
	}
}
