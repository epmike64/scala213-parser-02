package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

public class fAccessQualifier extends AstOperandNod {
	public enum Kind { ID, THIS}
	public final Kind kind;
	public final fNamedToken id; // only if kind == ID
	public fAccessQualifier(Kind kind, fNamedToken id) {
		this.kind = kind;
		if (kind == Kind.ID) {
			assert id != null;
			this.id = id;
		} else {
			assert id == null;
			this.id = null;
		}
	}

	public Kind getKind() {
		return kind;
	}

	public fNamedToken getId() {
		return id;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}
}
