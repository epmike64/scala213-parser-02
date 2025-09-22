package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;
import java.util.Optional;

public class fParamClauses extends AstOperandNod {
	private Optional<List<List<fParam>>> paramsLists = Optional.empty();
	private Optional<List<fParam>> implicitParams = Optional.empty();

	public void addParams(List<fParam> params) {
		if (!paramsLists.isPresent()) {
			paramsLists = Optional.of(new java.util.ArrayList<>());
			return;
		}
		paramsLists.get().add(params);
	}

	public void setImplicitParams(List<fParam> implicitParams) {
		assert implicitParams != null && !implicitParams.isEmpty();
		this.implicitParams = Optional.of(implicitParams);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public Optional<List<List<fParam>>> getParams() {
		return paramsLists;
	}

	public Optional<List<fParam>> getImplicitParams() {
		return implicitParams;
	}


	@Override
	public String toString() {
		return "fParamClauses{" +
				"paramsLists=" + paramsLists +
				", implicitParams=" + implicitParams +
				'}';
	}
}
