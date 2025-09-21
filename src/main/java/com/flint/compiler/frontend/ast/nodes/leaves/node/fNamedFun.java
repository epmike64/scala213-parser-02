package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.Optional;

public class fNamedFun extends fFun {
	private final fFunSig funSig;
	private Optional<fType> returnType = Optional.empty();
	private Optional<AstOperandNod> bd = Optional.empty();

	public fNamedFun(Optional<fModifiers> mods, fFunSig funSig) {
		super(mods);
		this.funSig = funSig;
	}

	public void setReturnType(fType rt) {
		if(this.returnType.isPresent()) {throw new IllegalStateException("Return type already set");}
		if(rt == null) throw new IllegalArgumentException("Return type cannot be null");
		this.returnType = Optional.of(rt);
	}

	public void setBody(AstOperandNod bd) {
		if(this.bd.isPresent()) throw new IllegalStateException("Body already set");
		if(bd == null) throw new IllegalArgumentException("Body cannot be null");
		this.bd = Optional.of(bd);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fFunSig getFunSig() {
		return funSig;
	}

	public Optional<fType> getReturnType() {
		return returnType;
	}
	public Optional<AstOperandNod> getBody() {
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
