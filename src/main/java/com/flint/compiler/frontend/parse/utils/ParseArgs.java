package com.flint.compiler.frontend.parse.utils;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.operators.AstEntitySepOpNod;
import com.flint.compiler.frontend.ast.nodes.operators.AstRootOpNod;

public class ParseArgs {

	public final AstRootOpNod rootOp = new AstRootOpNod();
	private AstNod lastAdded = rootOp;

	public void setRight(AstNod astRightN) {
		lastAdded.setAstRightN(astRightN);
		lastAdded = astRightN;
	}




	public AstNod getLastAdded() {
		return lastAdded;
	}
}
