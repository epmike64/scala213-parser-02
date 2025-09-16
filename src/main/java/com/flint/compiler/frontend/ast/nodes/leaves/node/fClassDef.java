package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fClassDef extends fTraitDef {
	private final boolean isCaseClass;
	private fClassParamClauses classParamClauses;


	public fClassDef(fNamedToken name, boolean isCaseClass, fModifiers modifiers) {
		super(name, modifiers);
		this.isCaseClass = isCaseClass;
	}

	public void setClassParamClauses(fClassParamClauses classParamClauses) {
		this.classParamClauses = classParamClauses;
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
}
