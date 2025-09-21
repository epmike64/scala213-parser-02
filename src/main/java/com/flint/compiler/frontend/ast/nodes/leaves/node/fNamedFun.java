package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fNamedFun extends fFun {
	private final fFunSig funSig;
	private fType returnType;
	private AstOperandNod body;

	public fNamedFun(fModifiers mods, fFunSig funSig) {
		super(mods);
		this.funSig = funSig;
	}

	public void setReturnType(fType returnType) {
		this.returnType = returnType;
	}

	public void setBody(AstOperandNod body) {
		this.body = body;
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
		return body;
	}
}
