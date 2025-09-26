package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fClassParents extends AstOperandNod {
	private final fClassConstr constr;
	private final List<fParamType> withTypes = new ArrayList<>();
	public fClassParents(fClassConstr constructor) {
		this.constr = constructor;
	}

	public void addWithType(fParamType wt) {
		withTypes.add(wt);
	}

	public fClassConstr getConstr() {
		return constr;
	}
	public List<fParamType> getWithTypes() {
		return withTypes;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fClassParents{" +
				"constr=" + constr +
				", withTypes=" + withTypes +
				'}';
	}
}
