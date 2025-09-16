package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

import java.util.List;

public class fParamClauses {
	private List<List<fParam>> paramsLists;
	private List<fParam> implicitParams;

	public void addParams(List<fParam> params) {
		if (paramsLists == null) {
			paramsLists = new java.util.ArrayList<>();
		}
		paramsLists.add(params);
	}

	public void setImplicitParams(List<fParam> implicitParams) {
		this.implicitParams = implicitParams;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
