package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

public class fAccessQualifier extends AstOperandNod {
	public enum Kind {ID, THIS}

	public final Kind kind;
	public final fNameValToken id; // only if kind == ID

	public fAccessQualifier(Kind kind, fNameValToken id) {
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

	public fNameValToken getId() {
		return id;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		if (kind == Kind.ID) {
			return "fAccessQualifier(ID: " + id.getTokValue() + ")";
		} else {
			return "fAccessQualifier(THIS)";
		}
	}
}
