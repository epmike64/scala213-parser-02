package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.Optional;

public class fModifiers extends AstOperandNod {

	private Optional<fAccessModifier> accessModifier  = Optional.empty();
	private Optional<fOverrideModifier> overrideModifier = Optional.empty();
	private Optional<fLocalModifier> localModifier = Optional.empty();

	public void setAccessModifier(fAccessModifier m) {
		if( this.accessModifier.isPresent()) throw new RuntimeException("Access modifier already set");
		if(m == null) throw new RuntimeException("Access modifier cannot be null");
		this.accessModifier = Optional.of(m);
	}
	public void setOverrideModifier(fOverrideModifier m) {
		if( this.overrideModifier.isPresent()) throw new RuntimeException("Override modifier already set");
		if(m == null) throw new RuntimeException("Override modifier cannot be null");
		this.overrideModifier = Optional.of(m);
	}
	public void setLocalModifier(fLocalModifier m) {
		if( this.localModifier.isPresent()) throw new RuntimeException("Local modifier already set");
		if(m == null) throw new RuntimeException("Local modifier cannot be null");
		this.localModifier = Optional.of(m);
	}
	public Optional<fAccessModifier> getAccessModifier() {
		return accessModifier;
	}
	public Optional<fOverrideModifier> getOverrideModifier() {
		return overrideModifier;
	}
	public Optional<fLocalModifier> getLocalModifier() {
		return localModifier;
	}

	@Override
	public void accept(com.flint.compiler.frontend.ast.nodes.AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fModifiers{" +
				"accessModifier=" + accessModifier +
				", overrideModifier=" + overrideModifier +
				", localModifier=" + localModifier +
				'}';
	}
}
