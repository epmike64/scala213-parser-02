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

	public void insertEntitySep(){
		assert lastAdded.isOperator() == false;
		AstEntitySepOpNod sepOp = new AstEntitySepOpNod();
		AstOperatorNod prn = lastAdded.getAstParentN();
      assert prn.isOperator();
		if(prn ==  rootOp){
			rootOp.setAstRightN(sepOp);
			sepOp.setAstLeftN(lastAdded);
		} else {
			AstOperatorNod grandParent = prn.getAstParentN();
			assert grandParent.isOperator() && grandParent.getAstRightN() == prn;//?
			grandParent.setAstRightN(sepOp);
			sepOp.setAstLeftN(prn);
		}
	}


	public AstNod getLastAdded() {
		return lastAdded;
	}
}
