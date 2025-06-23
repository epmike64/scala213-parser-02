package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

public class fClassParam extends AstOperandNod {
	private fValVar valVar = fValVar.NONE;
	private NamedToken identifier;
	private fParamType paramType;
	private AstProdSubTreeN defaultValue;

	public  void  setValVar(fValVar valVar) {
		this.valVar = valVar;
	}
	public void setIdentifier(NamedToken identifier) {
		this.identifier = identifier;
	}
	public void setParamType(fParamType paramType) {
		this.paramType = paramType;
	}
	public void setDefaultValue(AstProdSubTreeN defaultValue) {
		this.defaultValue = defaultValue;
	}
}
