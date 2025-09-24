package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.Optional;

public class fClassDef extends fTraitDef {
	private final boolean isCaseClass;
	private Optional<fClassParamClauses> classParamClauses = Optional.empty();
	private Optional<fAccessModifier> constrAccessModifier = Optional.empty();

	public fClassDef(fNameValToken name, boolean isCaseClass, Optional<fModifiers> modifiers) {
		super(name, modifiers);
		this.isCaseClass = isCaseClass;
	}

	public void setClassParamClauses(fClassParamClauses cpcs) {
		if(classParamClauses.isPresent()) throw new RuntimeException("Class parameter clauses already set");
		if(cpcs == null) throw new RuntimeException("Class parameter clauses cannot be null");
		this.classParamClauses = Optional.of(cpcs);
	}

	public void setConstrAccessModifier(fAccessModifier cam) {
		if(constrAccessModifier.isPresent() ) throw new RuntimeException("Constructor access modifier already set");
		if(cam == null) throw new RuntimeException("Constructor access modifier cannot be null");
		this.constrAccessModifier = Optional.of(cam);
	}

	public Optional<fAccessModifier> getConstrAccessModifier() {
		return constrAccessModifier;
	}
	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public boolean isCaseClass() {
		return isCaseClass;
	}

	public Optional<fClassParamClauses> getClassParamClauses() {
		return classParamClauses;
	}

	@Override
	public String toString() {
		return "fClassDef{" +
				"isCaseClass=" + isCaseClass +
				", className=" + getName() +
				", classParamClauses=" + classParamClauses +
				", constructorAccessModifier=" + constrAccessModifier +
				", modifiers=" + getModifiers() +
				", typeParams=" + getTypeParams() +
				'}';
	}
}
