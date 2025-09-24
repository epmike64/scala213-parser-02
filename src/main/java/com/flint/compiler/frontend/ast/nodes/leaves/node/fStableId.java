package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.ArrayList;
import java.util.List;

public class fStableId extends AstOperandNod {

	public static class fTPair {
		final fToken id;
		final fTokenKind kind;

		fTPair(fToken id, fTokenKind kind) {
			assert id != null && kind != null;
			this.id = id;
			this.kind = kind;
		}

		@Override
		public String toString() {
			return "fTPair{" +
					"id=" + id +
					", kind=" + kind +
					'}';
		}
	}

	private final boolean isPath;
	private boolean isKwType = false;

	public fStableId(boolean isPath) {
		this.isPath = isPath;
	}

	private final List<fTPair> tpairs = new ArrayList<>();

   private void addTPair(fTPair token) {
      assert isKwType == false;
      tpairs.add(token);
   }
	public void addTId(fNameValToken tId) {
      addTPair(new fTPair(tId, fTokenKind.T_ID));
	}

	public void addSuper(fToken s) {
		assert s.kind == fTokenKind.T_SUPER;
		addTPair(new fTPair(s, fTokenKind.T_SUPER));
	}

	public void addThis(fToken t) {
		assert t.kind == fTokenKind.T_THIS;
		addTPair(new fTPair(t, fTokenKind.T_THIS));
	}

	public void addClassQualifier(fNameValToken tId) {
		addTPair(new fTPair(tId, fTokenKind.T_CLASS_QUALIFIER));
	}

	public fTokenKind getLastTKind() {
		if (tpairs.isEmpty()) {
			return null;
		}
		return tpairs.get(tpairs.size() - 1).kind;
	}

   public void setKwType(fToken t){
      assert t.kind == fTokenKind.T_TYPE;
      isKwType = true;
   }

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public boolean isPath() {
		return isPath;
	}

	public boolean isKwType() {
		return isKwType;
	}

	@Override
	public String toString() {
		return "fStableId{" +
				"isPath=" + isPath +
				", isKwType=" + isKwType +
				", tpairs=" + tpairs +
				'}';
	}
}