package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.List;

public class fIds extends AstOperandNod {
	private final List<fNameValToken> ids;
	public fIds(List<fNameValToken> ids) {
		this.ids = ids;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
	public List<fNameValToken> getIds() {
		return ids;
	}
	@Override
	public String toString() {
		return "fIds{" +
				"ids=" + ids +
				'}';
	}
}
