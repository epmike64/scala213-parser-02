package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fFuncDef extends AstOperandNod {
	private final NamedToken funcName;
	private List<fTypeParam> typeParams;
	private fParamClauses paramClauses;
	private fType returnType;
	private AstProdSubTreeN body;
	private fConstrBlock constructorBlock;

	public static fFuncDef getNamedFunDef(NamedToken name) {
		return new fFuncDef(name);
	}

	public static fFuncDef getTHISFunDef(fToken t) {
		assert t.kind == fTokenKind.T_THIS;
		return new fFuncDef(null);
	}

	private fFuncDef(NamedToken token) {
		this.funcName = token;
	}

	public boolean isThis() {
		return funcName == null;
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

	public void setConstrBlock(fConstrBlock constructorBlock) {
		this.constructorBlock = constructorBlock;
	}
}
