package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class fValue extends AstOperandNod {
	private final Optional<fModifiers> modifiers;
	private final List<AstProdSubTreeN> names = new ArrayList<>();
	private Optional<fType> type = Optional.empty();
	private Optional<AstProdSubTreeN> assignExpr = Optional.empty();
	public fValue(Optional<fModifiers> modifiers) {
		this.modifiers = modifiers;
	}
	public void addName(AstProdSubTreeN name) {
		this.names.add(name);
	}
	public void setType(fType type) {
		if( this.type.isPresent()) throw new RuntimeException("Type already set");
		if(type == null) throw new RuntimeException("Type cannot be null");
		this.type = Optional.of(type);
	}
	public void setAssignExpr(AstProdSubTreeN assignExpr) {
		if( this.assignExpr.isPresent()) throw new RuntimeException("Assign expression already set");
		if(assignExpr == null) throw new RuntimeException("Assign expression cannot be null");
		this.assignExpr = Optional.of(assignExpr);
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	public List<AstProdSubTreeN> getNames() {
		return names;
	}

	public Optional<fModifiers> getModifiers() {
		return modifiers;
	}

	public Optional<fType> getType() {
		return type;
	}

	public Optional<AstProdSubTreeN> getAssignExpr() {
		return assignExpr;
	}

	@Override
	public String toString() {
		return "fValue{" +
				"modifiers=" + modifiers +
				", names=" + names +
				", type=" + type +
				", assignExpr=" + assignExpr +
				'}';
	}
}
