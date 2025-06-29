package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.ArrayList;
import java.util.List;

public class fStableId extends AstOperandNod {

	public static class fTPair {
		final fToken id;
		final fTokenKind kind;

		fTPair(fToken id, fTokenKind kind) {
			this.id = id;
			this.kind = kind;
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
	public void addTId(NamedToken tId) {
      addTPair(new fTPair(tId, fTokenKind.T_ID));
	}

	public void addSuper(fToken s) {
		assert s.kind == fTokenKind.T_SUPER;
		addTPair(new fTPair(null, fTokenKind.T_SUPER));
	}

	public void addThis(fToken t) {
		assert t.kind == fTokenKind.T_THIS;
		addTPair(new fTPair(null, fTokenKind.T_THIS));
	}

	public void addClassQualifier(NamedToken tId) {
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
}