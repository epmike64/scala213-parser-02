package com.flint.compiler.frontend.ast.nodes.leaves;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.ast.nodes.AstNod;

public class AstSubTreeNod extends AstOperandNod {
	public final AstRootOpNod astRootOpNode;
	public final AstNod lastAdded;
	public AstSubTreeNod(AstRootOpNod astRootOpNode, AstNod lastAdded) {
		this.astRootOpNode = astRootOpNode;
		this.lastAdded = lastAdded;
	}

	@Override
	public AstNodKind astNKind() {
		return null;
	}
}
