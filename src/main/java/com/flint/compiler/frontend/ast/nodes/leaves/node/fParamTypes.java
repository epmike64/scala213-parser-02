package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;

public class fParamTypes extends AstOperandNod {
		private final List<fParamType> paramTypes;
		public fParamTypes(List<fParamType> paramTypes) {
			this.paramTypes = paramTypes;
		}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fParamTypes{" +
				"paramTypes=" + paramTypes +
				'}';
	}
}
