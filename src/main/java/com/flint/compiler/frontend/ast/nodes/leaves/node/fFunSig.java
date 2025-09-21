package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;

public class fFunSig extends AstOperandNod {
	private final fNamedToken name;
	private fParamClauses paramClauses;
	private List<fTypeParam> typeParams;
	public fFunSig(fNamedToken name) {
		this.name = name;
	}
	public void setParamClauses(fParamClauses paramClauses) {
		this.paramClauses = paramClauses;
	}
	public void setTypeParams(List<fTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNamedToken getName() {
		return name;
	}

	public fParamClauses getParamClauses() {
		return paramClauses;
	}

	public List<fTypeParam> getTypeParams() {
		return typeParams;
	}
}
