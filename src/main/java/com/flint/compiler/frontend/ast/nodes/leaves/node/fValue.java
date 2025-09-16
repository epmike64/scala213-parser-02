package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

import java.util.ArrayList;
import java.util.List;

public class fValue extends AstOperandNod {

	private final fModifiers modifiers;
	private final List<AstProdSubTreeN> names = new ArrayList<>();
	private fType type;
	private AstProdSubTreeN assignExpr;
	public fValue(fModifiers modifiers) {
		this.modifiers = modifiers;
	}
	public void addName(AstProdSubTreeN name) {
		this.names.add(name);
	}
	public void setType(fType type) {
		this.type = type;
	}
	public void setAssignExpr(AstProdSubTreeN assignExpr) {
		this.assignExpr = assignExpr;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}
}
