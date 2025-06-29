package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

import java.util.ArrayList;
import java.util.List;

public class fImport extends AstOperandNod {

	public static class fImportSelector {
		private final NamedToken from, to;
		public fImportSelector(final NamedToken from, final NamedToken to) {
			this.from = from;
			this.to = to;
		}
	}

	public static class fImportExpr {
		private final fStableId id;
		private List<fImportSelector> selectors = null;
		public fImportExpr(final fStableId id) {
			this.id = id;
		}

		public void setSelectors(final List<fImportSelector> selectors) {
			assert  this.selectors == null;
			this.selectors = selectors;
		}
	}



	private final List<fImportExpr> importExprs = new ArrayList<>();
	public void addImportExpr(fImportExpr importExpr) {
		importExprs.add(importExpr);
	}
}
