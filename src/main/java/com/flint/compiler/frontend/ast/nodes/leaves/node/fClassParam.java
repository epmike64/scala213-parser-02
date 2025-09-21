package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

public class fClassParam extends AstOperandNod {
	private fMutabilityType mutability = fMutabilityType.NONE;
	private fNamedToken identifier;
	private fParamType paramType;
	private AstProdSubTreeN defaultValue;
	private fModifiers modifiers;
	public  void setMutability(fMutabilityType mutability) {
		this.mutability = mutability;
	}
	public void setIdentifier(fNamedToken identifier) {
		this.identifier = identifier;
	}
	public void setParamType(fParamType paramType) {
		this.paramType = paramType;
	}
	public void setDefaultValue(AstProdSubTreeN defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setModifiers(fModifiers modifiers) {
		this.modifiers = modifiers;
	}
	public fMutabilityType getMutability() {
		return mutability;
	}
	public fNamedToken getIdentifier() {
		return identifier;
	}
	public fParamType getParamType() {
		return paramType;
	}
	public AstProdSubTreeN getDefaultValue() {
		return defaultValue;
	}
	public fModifiers getModifiers() {
		return modifiers;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
	@Override
	public String toString() {
		return "fClassParam{" +
				"mutability=" + mutability +
				", identifier=" + identifier +
				", paramType=" + paramType +
				", defaultValue=" + defaultValue +
				", modifiers=" + modifiers +
				'}';
	}
}
