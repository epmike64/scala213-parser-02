package com.flint.compiler.frontend.ast.nodes;

import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.parse.lex.token.fLangOperKind;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class AstOperatorNod extends AstNod {
	public final fLangOperKind kind;
	public final fToken operatorToken;

	public AstOperatorNod(fLangOperKind kind, fToken operatorToken) {
		this.kind = kind;
		this.operatorToken = operatorToken;
	}

	@Override
	public AstNodKind astNKind(){
		return AstNodKind.AST_OPERATOR;
	}

	@Override
	public boolean isOperator() {
		return true;
	}

	public fLangOperKind getLangOperatorKind() {
		return kind;
	}

	@Override
	public void setAstRightN(AstNod astRightN) {
		this.astRightN = astRightN;
		astRightN.setAstParentN(this);
	}


	@Override
	public void setAstLeftN(AstNod astLeftN) {
		this.astLeftN = astLeftN;
		astLeftN.setAstParentN(this);
	}

	@Override
	public fToken getFirstToken() {
		return operatorToken;
	}
}
