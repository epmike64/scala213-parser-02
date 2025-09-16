package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.ArrayList;
import java.util.List;

public class fModifiers extends AstOperandNod {
	private final List<fToken> modifiers = new ArrayList<>();
	public fModifiers() {
	}
	public void addModifier(fToken token) {
		modifiers.add(token);
	}
	public void addSpecialModifier(fToken token) {
		throw new RuntimeException("Not implemented");
	}
	public List<fToken> getModifiers() {
		return modifiers;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
