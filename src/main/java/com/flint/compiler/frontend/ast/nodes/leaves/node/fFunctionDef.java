package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fFunctionDef extends AstOperandNod {
	private final NamedToken name;
	private List<fTypeParam> typeParams;
	private fParamClauses paramClauses;
	private fType returnType;
	private AstProdSubTreeN body;

	public fFunctionDef(NamedToken name) {
		this.name = name;
	}

	@Override
	public fToken getFirstToken() {
		return null;
	}

	public void setTypeParams(List<fTypeParam> params){
		this.typeParams = params;
	}

	public void setParamClauses(fParamClauses paramClauses) {
		this.paramClauses = paramClauses;
	}

	public void setReturnType(fType returnType) {
		this.returnType = returnType;
	}

	public void setBody(AstProdSubTreeN body) {
		this.body = body;
	}
}
