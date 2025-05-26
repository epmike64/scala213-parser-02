package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;

public class AstSubTreeNod extends AstOperandNod {
	public final AstRootOpNod rootOpNod;
	public AstSubTreeNod(AstRootOpNod rootOpNod) {
		this.rootOpNod = rootOpNod;
	}

	@Override
	public AstNodKind astNKind() {
		return null;
	}
}
