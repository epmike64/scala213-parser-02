package com.flint.compiler.frontend.ast.nodes.operators;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class AstRootOpNod extends AstOperatorNod {

	public AstRootOpNod() {
		super(fLangOperatorKind.O_ROOT, fToken.ROOT_OPERATOR);
	}

	@Override
	public void setAstParentN(AstOperatorNod astParentN) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public void setAstLeftN(AstNod astLeftN) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public AstNodKind astNKind() {
		return AstNodKind.AST_ROOT_OPERATOR;
	}
}
