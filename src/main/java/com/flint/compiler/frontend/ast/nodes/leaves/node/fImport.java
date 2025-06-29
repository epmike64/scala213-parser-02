package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fImport extends AstOperandNod {
	private final fStableId stableId;
	public fImport(fStableId stableId) {
		this.stableId = stableId;
	}
}
