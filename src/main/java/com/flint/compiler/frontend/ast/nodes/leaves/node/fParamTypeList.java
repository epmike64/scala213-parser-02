package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;

public class fParamTypeList extends AstOperandNod {
		private final List<fParamType> paramTypes;
		public fParamTypeList(List<fParamType> paramTypes) {
			this.paramTypes = paramTypes;
		}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
