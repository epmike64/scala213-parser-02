package com.flint.compiler.frontend.ast.nodes.leaves.node;

public class fAccessModifier extends fModifier{
	public enum AccessModKind { PRIVATE, PROTECTED }

	private final AccessModKind kind;
	private fAccessQualifier qualifier;

	public fAccessModifier(AccessModKind kind) {
		this.kind = kind;
	}
	public void setQualifier(fAccessQualifier qualifier) {
		this.qualifier = qualifier;
	}
	public fAccessQualifier getQualifier() {
		return qualifier;
	}
	public AccessModKind getKind() {
		return kind;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		fAccessModifier that = (fAccessModifier) o;
		return kind == that.kind;
	}

	@Override
	public int hashCode() {
		return kind.hashCode();
	}
}
