package com.flint.compiler.frontend.parse.utils;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;

public class Ast {

	public final AstRootOpNod rootOp = new AstRootOpNod();
	private AstNod astLastN = rootOp;
	private boolean isContinue = true;

	public void setRight(AstNod v) {
		assert astLastN.isOperator(): "astRightN must be an operator";
		astLastN.setAstRightN(v);
		astLastN = v;
	}

	public void setAstLastN(AstNod v){
		astLastN = v;
	}

	public AstNod astLastN() {
		return astLastN;
	}

	public AstNodKind astLastNKnd() {
		return astLastN.astNKind();
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}
}
