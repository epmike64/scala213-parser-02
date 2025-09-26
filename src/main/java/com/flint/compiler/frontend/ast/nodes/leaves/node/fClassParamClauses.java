package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;


public class fClassParamClauses extends AstOperandNod {
	private List<fClassParam> implicitParams;
	private final List<List<fClassParam>> params = new ArrayList<>();

	public void setImplicitParams(List<fClassParam> implicitParams) {
		this.implicitParams = implicitParams;
	}

	public void addParams(List<fClassParam> ps) {
		this.params.add(ps);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<fClassParam> getImplicitParams() {
		return implicitParams;
	}

	public List<List<fClassParam>> getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "fClassParamClauses{" +
				"implicitParams=" + implicitParams +
				", params=" + params +
				'}';
	}
}
