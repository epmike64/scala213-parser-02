package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fClassParamClauses extends AstOperandNod  {
	private List<fClassParam> implicitParams;
	private final List<List<fClassParam>> params = new ArrayList<>();

	public void setImplicitParams(List<fClassParam> implicitParams) {
		this.implicitParams = implicitParams;
	}

	public void addParams(List<fClassParam> params) {
		this.params.add(params);
	}
}
