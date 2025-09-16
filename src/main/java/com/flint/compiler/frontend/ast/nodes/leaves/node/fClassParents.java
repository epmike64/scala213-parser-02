package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fClassParents extends AstOperandNod {
	private fClassConstr constructor;
	private List<fParamType> withTypes = new ArrayList<>();
	public fClassParents(fClassConstr constructor) {
		this.constructor = constructor;
	}
	public void addWithType(fParamType withType) {
		withTypes.add(withType);
	}

	public fClassConstr getConstructor() {
		return constructor;
	}
	public List<fParamType> getWithTypes() {
		return withTypes;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
