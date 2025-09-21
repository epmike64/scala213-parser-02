package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;
import java.util.Optional;

public class fFunSig extends AstOperandNod {
	private final fNamedToken name;
	private Optional<fParamClauses> paramClauses = Optional.empty();
	private Optional<List<fTypeParam>> typeParams = Optional.empty();

	public fFunSig(fNamedToken name) {
		this.name = name;
	}
	public void setParamClauses(fParamClauses paramClauses) {
		if(this.paramClauses.isPresent()) {
			throw new IllegalStateException("Parameter clauses already set");
		}
		if (paramClauses == null) {
			throw new IllegalArgumentException("Parameter clauses cannot be null");
		}
		this.paramClauses = Optional.of(paramClauses);
	}
	public void setTypeParams(List<fTypeParam> typeParams) {
		if(this.typeParams.isPresent()) {
			throw new IllegalStateException("Type parameters already set");
		}
		if (typeParams == null || typeParams.isEmpty()) {
			throw new IllegalArgumentException("Type parameters cannot be null or empty");
		}
		this.typeParams = Optional.of(typeParams);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fNamedToken getName() {
		return name;
	}

	public Optional<fParamClauses> getParamClauses() {
		return paramClauses;
	}

	public Optional<List<fTypeParam>> getTypeParams() {
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
