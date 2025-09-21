package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.Optional;

public class fClassConstr extends AstOperandNod {
	private final fParamType fParamType;
	private Optional<AstProdSubTreeN> args = Optional.empty();

	public fClassConstr(fParamType fParamType) {
		this.fParamType = fParamType;
	}

	public void setArgExprs(AstProdSubTreeN as) {
		if(this.args.isPresent()) {throw new IllegalStateException("Args already set");}
		if(as == null) {throw new IllegalArgumentException("Args cannot be null");}
		this.args = Optional.of(as);
	}

	public fParamType getParamType() {
		return fParamType;
	}
	public Optional<AstProdSubTreeN> getArgs() {
		return args;
	}


	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fClassConstr{" +
				"fParamType=" + fParamType +
				", args=" + args +
				'}';
	}
}
