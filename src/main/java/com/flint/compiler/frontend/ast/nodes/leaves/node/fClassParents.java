package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fClassParents extends AstOperandNod {
	private fClassConstructor constructor;
	private List<fParamType> withTypes = new ArrayList<>();
	public fClassParents(fClassConstructor constructor) {
		this.constructor = constructor;
	}
	public void addWithType(fParamType withType) {
		withTypes.add(withType);
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
