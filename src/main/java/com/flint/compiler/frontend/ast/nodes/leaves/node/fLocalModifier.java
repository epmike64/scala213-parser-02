package com.flint.compiler.frontend.ast.nodes.leaves.node;


public class fLocalModifier extends fModifier {

	public enum LocalModKind { ABSTRACT, FINAL, SEALED, IMPLICIT, LAZY }
	private final LocalModKind kind;

	public fLocalModifier(LocalModKind kind) {
		this.kind = kind;
	}

	public LocalModKind getKind() {
		return kind;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		fLocalModifier that = (fLocalModifier) o;
		return kind == that.kind;
	}

	@Override
	public int hashCode() {
		return kind.hashCode();
	}
}
