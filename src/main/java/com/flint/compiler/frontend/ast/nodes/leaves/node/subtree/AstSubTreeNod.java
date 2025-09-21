package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class AstSubTreeNod extends AstOperandNod {
	public final AstRootOpNod rootOpNod;
	public AstSubTreeNod(AstRootOpNod rootOpNod) {
		this.rootOpNod = rootOpNod;
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}
}
