package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.Optional;

public class fClassParam extends AstOperandNod {
	private fMutabilityType mutability = fMutabilityType.NONE;
	private fNameValToken identifier;
	private Optional<fParamType> paramType = Optional.empty();
	private Optional<AstProdSubTreeN> defaultValue = Optional.empty();
	private Optional<fModifiers> modifiers = Optional.empty();
	public  void setMutability(fMutabilityType mutability) {this.mutability = mutability;}
	public void setIdentifier(fNameValToken identifier) {this.identifier = identifier;}
	public void setParamType(fParamType paramType) {
		if(this.paramType.isPresent()) throw new RuntimeException("Param type already set");
		if(paramType == null) throw new RuntimeException("Param type cannot be null");
		this.paramType = Optional.of(paramType);
	}
	public void setDefaultValue(AstProdSubTreeN df) {
		if(this.defaultValue.isPresent()) throw new RuntimeException("Default value already set");
		if(df == null) throw new RuntimeException("Default value cannot be null");
		this.defaultValue = Optional.of(df);
	}
	public void setModifiers(Optional<fModifiers> mds) {
		if(this.modifiers.isPresent()) throw new RuntimeException("Modifiers already set");
		this.modifiers = mds;
	}

	public fMutabilityType getMutability() {
		return mutability;
	}
	public fNameValToken getIdentifier() {
		return identifier;
	}
	public Optional<fParamType> getParamType() {
		return paramType;
	}
	public Optional<AstProdSubTreeN> getDefaultValue() {
		return defaultValue;
	}
	public Optional<fModifiers> getModifiers() {
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
