package com.flint.compiler.frontend.parse.utils;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;

public class Ast {

	public final AstRootOpNod rootOp = new AstRootOpNod();
	private AstNod astLastN = rootOp;

	public ParenClosure astParClosure = null;

	public static class ParenClosure {
		public final Ast ast;
		public final int lparSz;

		public ParenClosure(Ast ast, int lparSz) {
			assert ast != null && lparSz >= 0;
			this.ast = ast;
			this.lparSz = lparSz;
		}
	}

	public void setRight(AstNod astRightN) {
		astLastN.setAstRightN(astRightN);
		astLastN = astRightN;
	}

	public AstNod astLastN() {
		return astLastN;
	}

	public AstNodKind astLastNKnd() {
		return astLastN.astNKind();
	}
}
