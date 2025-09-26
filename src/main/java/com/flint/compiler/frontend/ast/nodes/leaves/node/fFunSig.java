package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.List;


public class fFunSig extends AstOperandNod {
	private final fNameValToken name;
	private fParamClauses paramClauses;
	private List<fTypeParam> typeParams;

	public fFunSig(fNameValToken name) {
		this.name = name;
	}

	public void setParamClauses(fParamClauses pc) {
		this.paramClauses = pc;
	}

	public void setTypeParams(List<fTypeParam> typeParams) {
		this.typeParams = typeParams;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNameValToken getName() {
		return name;
	}

	public fParamClauses getParamClauses() {
		return paramClauses;
	}

	public List<fTypeParam> getTypeParams() {
		return typeParams;
	}

	@Override
	public String toString() {
		return "fFunSig{" +
				"name=" + name +
				", paramClauses=" + paramClauses +
				", typeParams=" + typeParams +
				'}';
	}
}
