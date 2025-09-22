package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.ArrayList;
import java.util.List;

public class fTypeArgs extends AstOperandNod {
	private final List<fType> typeArgs = new ArrayList<>();
	public fTypeArgs(List types) {
		assert  types != null && !types.isEmpty();
		this.typeArgs.addAll(types);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<fType> getTypeArgs() {
		return typeArgs;
	}

	@Override
	public String toString() {
		return "fTypeArgs{" +
				"typeArgs=" + typeArgs +
				'}';
	}
}
