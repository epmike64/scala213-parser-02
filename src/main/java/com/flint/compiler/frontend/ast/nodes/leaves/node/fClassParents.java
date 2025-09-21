package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;
import java.util.Optional;

public class fClassParents extends AstOperandNod {
	private final fClassConstr constr;
	private Optional<List<fParamType>> withTypes = Optional.empty();
	public fClassParents(fClassConstr constructor) {
		this.constr = constructor;
	}
	public void addWithType(fParamType wt) {
		if(wt == null) {throw new IllegalArgumentException("With type cannot be null");}
		if(!withTypes.isPresent()) {withTypes = Optional.of(new java.util.ArrayList<>());}
		withTypes.get().add(wt);
	}

	public fClassConstr getConstr() {
		return constr;
	}
	public Optional<List<fParamType>> getWithTypes() {
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
