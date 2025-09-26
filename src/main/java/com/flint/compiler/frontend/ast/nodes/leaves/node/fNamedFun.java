package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fNamedFun extends fFun {
	private final fFunSig funSig;
	private fType returnType;
	private AstOperandNod bd;

	public fNamedFun(fModifiers mods, fFunSig funSig) {
		super(mods);
		this.funSig = funSig;
	}

	public void setReturnType(fType rt) {
		this.returnType = rt;
	}

	public void setBody(AstOperandNod bd) {
		this.bd = bd;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fFunSig getFunSig() {
		return funSig;
	}

	public fType getReturnType() {
		return returnType;
	}
	public AstOperandNod getBody() {
		return bd;
	}

	@Override
	public String toString() {
		return "fNamedFun{" +
				"mods=" + getMods() +
				", funSig=" + funSig +
				", returnType=" + returnType +
				", body=" + bd +
				'}';
	}
}
