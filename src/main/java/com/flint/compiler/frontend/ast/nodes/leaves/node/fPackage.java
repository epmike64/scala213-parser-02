package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.ArrayList;
import java.util.List;

public class fPackage extends AstOperandNod {
	private final List<fNameValToken> fIds;

	public fPackage(List<fNameValToken> ids) {
		this.fIds = ids;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<fNameValToken> getIds() {
		return fIds;
	}

	@Override
	public String toString() {
		return "fPackage{" +
				"fIds=" + fIds +
				'}';
	}
}
