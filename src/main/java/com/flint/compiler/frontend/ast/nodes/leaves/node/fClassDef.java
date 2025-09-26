package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;


public class fClassDef extends fTraitDef {
	private final boolean isCaseClass;
	private fClassParamClauses classParamClauses;
	private fAccessModifier constrAccessModifier;

	public fClassDef(fNameValToken name, boolean isCaseClass, fModifiers modifiers) {
		super(name, modifiers);
		this.isCaseClass = isCaseClass;
	}

	public void setClassParamClauses(fClassParamClauses cpcs) {
		this.classParamClauses = cpcs;
	}

	public void setConstrAccessModifier(fAccessModifier cam) {
		this.constrAccessModifier = cam;
	}

	public fAccessModifier getConstrAccessModifier() {
		return constrAccessModifier;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public boolean isCaseClass() {
		return isCaseClass;
	}

	public fClassParamClauses getClassParamClauses() {
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
