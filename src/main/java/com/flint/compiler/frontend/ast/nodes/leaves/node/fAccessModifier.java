package com.flint.compiler.frontend.ast.nodes.leaves.node;

import java.util.Optional;

public class fAccessModifier extends fModifier{
	public enum AccessModKind { PRIVATE, PROTECTED }

	private final AccessModKind kind;
	private Optional<fAccessQualifier> qualifier = Optional.empty();

	public fAccessModifier(AccessModKind kind) {
		this.kind = kind;
	}
	public void setQualifier(fAccessQualifier q) {
		if(qualifier.isPresent()) throw new IllegalStateException("Qualifier already set");
		if(q == null) throw new IllegalArgumentException("Qualifier cannot be null");
		this.qualifier = Optional.of(q);
	}

	public Optional<fAccessQualifier> getQualifier() {
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

	@Override
	public String toString() {
		return "fAccessModifier{" +
				"kind=" + kind +
				", qualifier=" + qualifier +
				'}';
	}

}
