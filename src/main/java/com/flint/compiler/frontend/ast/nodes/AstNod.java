package com.flint.compiler.frontend.ast.nodes;

import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public abstract class AstNod {
	protected AstNod astLeftN, astRightN;
	protected AstOperatorNod astParentN;

	public AstNod getAstLeftN() {
		return astLeftN;
	}
	public AstNod getAstRightN() {
		return astRightN;
	}
	public AstOperatorNod getAstParentN() {
		return astParentN;
	}

	public abstract AstNodKind astNKind();
	public abstract boolean isOperator();
	public void setAstParentN(AstOperatorNod astParentN) {
		this.astParentN = astParentN;
	}
	public abstract void setAstLeftN(AstNod astLeftN);
	public abstract void setAstRightN(AstNod astRightN);

}

