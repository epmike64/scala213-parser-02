package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.ArrayList;
import java.util.List;

public class fPackage extends AstOperandNod {
	private final List<fNamedToken> fIds;
	public fPackage(List<fNamedToken> ids) {
		assert ids != null && ids.size() > 0;
		this.fIds = new ArrayList<>(ids);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
