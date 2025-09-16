package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

public class fAccessModifier extends AstOperandNod {
	public enum Kind { PRIVATE, PROTECTED }
	private final Kind kind;
	private fAccessQualifier qualifier;
	public fAccessModifier(Kind kind) {
		this.kind = kind;
	}
	public void setQualifier(fAccessQualifier qualifier) {
		this.qualifier = qualifier;
	}
	public fAccessQualifier getQualifier() {
		return qualifier;
	}
	public Kind getKind() {
		return kind;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
