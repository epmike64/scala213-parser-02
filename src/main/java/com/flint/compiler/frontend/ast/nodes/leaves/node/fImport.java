package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.ArrayList;
import java.util.List;

public class fImport extends AstOperandNod {

	public static class fImportSelector {
		private final fNamedToken from, to;
		public fImportSelector(final fNamedToken from, final fNamedToken to) {
			this.from = from;
			this.to = to;
		}
		public fNamedToken getFrom() {
			return from;
		}
		public fNamedToken getTo() {
			return to;
		}
		@Override
		public String toString() {
			return "fImportSelector{" +
					"from=" + from +
					", to=" + to +
					'}';
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

		public fStableId getId() {
			return id;
		}
		public List<fImportSelector> getSelectors() {
			return selectors;
		}
		@Override
		public String toString() {
			return "fImportExpr{" +
					"id=" + id +
					", selectors=" + selectors +
					'}';
		}
	}



	private final List<fImportExpr> importExprs = new ArrayList<>();
	public void addImportExpr(fImportExpr importExpr) {
		importExprs.add(importExpr);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<fImportExpr> getImportExprs() {
		return importExprs;
	}

	@Override
	public String toString() {
		return "fImport{" +
				"importExprs=" + importExprs +
				'}';
	}
}
