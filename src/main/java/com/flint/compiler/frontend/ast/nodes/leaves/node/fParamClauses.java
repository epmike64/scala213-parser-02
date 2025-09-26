package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fParamClauses extends AstOperandNod {
	private final List<List<fParam>> paramsLists = new ArrayList<>();
	private List<fParam> implicitParams;

	public void addParams(List<fParam> params) {
		paramsLists.add(params);
	}

	public void setImplicitParams(List<fParam> implicitParams) {
		this.implicitParams = implicitParams;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<List<fParam>> getParams() {
		return paramsLists;
	}

	public List<fParam> getImplicitParams() {
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
