package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.OpChar;

import java.util.Optional;

public class fNamedFun extends fFun {
	private final fFunSig funSig;
	private fType returnType;
	private AstOperandNod body;

	public fNamedFun(Optional<fModifiers> mods, fFunSig funSig) {
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

	@Override
	public String toString() {
		return "fNamedFun{" +
				"mods=" + getMods() +
				", funSig=" + funSig +
				", returnType=" + returnType +
				", body=" + body +
				'}';
	}
}
