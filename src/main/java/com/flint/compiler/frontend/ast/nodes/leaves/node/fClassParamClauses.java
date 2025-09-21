package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class fClassParamClauses extends AstOperandNod  {
	private Optional<List<fClassParam>> implicitParams = Optional.empty();
	private Optional<List<List<fClassParam>>> params = Optional.empty();

	public void setImplicitParams(List<fClassParam> implicitParams) {
		if (this.implicitParams.isPresent()) throw new IllegalStateException("Implicit parameters already set");
		if (implicitParams == null || implicitParams.isEmpty()) throw new IllegalArgumentException("Implicit parameters cannot be null or empty");
		this.implicitParams = Optional.of(implicitParams);
	}

	public void addParams(List<fClassParam> params) {
		if (params == null || params.isEmpty()) throw new IllegalArgumentException("Parameters cannot be null or empty");
		if (!this.params.isPresent()) this.params = Optional.of(new ArrayList<>());
		this.params.get().add(params);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public Optional<List <fClassParam>> getImplicitParams() {
		return implicitParams;
	}

	public Optional<List<List<fClassParam>>> getParams() {
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
