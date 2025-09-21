package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public abstract class fLiteral extends AstOperandNod {
	private final fToken token;

	public fLiteral(fToken token) {
		this.token = token;
	}

	public static class fIntLit extends fLiteral {
		public fIntLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fIntLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	public static class fBoolLit extends fLiteral {
		public fBoolLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fBoolLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	public static class fStringLit extends fLiteral {
		public fStringLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fStringLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	public static class fFloatLit extends fLiteral {
		public fFloatLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fFloatLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	public static class fCharLit extends fLiteral {
		public fCharLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fCharLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	public static class fNullLit extends fLiteral {
		public fNullLit(fToken token) {
			super(token);
		}
		@Override
		public String toString() {
			return "fNullLit{" +
					"token=" + getToken() +
					'}';
		}
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public fToken getToken() {
		return token;
	}
}