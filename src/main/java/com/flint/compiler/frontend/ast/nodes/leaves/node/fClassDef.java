package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

public class fClassDef extends fTraitDef {
	private final boolean isCaseClass;
	private fClassParamClauses classParamClauses;
	private fAccessModifier constructorAccessModifier;

	public fClassDef(fNamedToken name, boolean isCaseClass, fModifiers modifiers) {
		super(name, modifiers);
		this.isCaseClass = isCaseClass;
	}

	public void setClassParamClauses(fClassParamClauses classParamClauses) {
		this.classParamClauses = classParamClauses;
	}

	public void setConstructorAccessModifier(fAccessModifier constructorAccessModifier) {
		this.constructorAccessModifier = constructorAccessModifier;
	}
	public fAccessModifier getConstrAccessModifier() {
		return constructorAccessModifier;
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
				"className=" + getName() +
				", classParamClauses=" + classParamClauses +
				", constructorAccessModifier=" + constructorAccessModifier +
				", modifiers=" + getModifiers() +
				", typeParams=" + getTypeParams() +
				'}';
	}
}
