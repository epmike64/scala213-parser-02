package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;

public class fParamType extends fType{
    public final boolean isFatArrow, isStar;

    public fParamType(fType t, boolean isFatArrow, boolean isStar) {
        super(t.getAstProdSubTreeN());
        this.isFatArrow = isFatArrow;
        this.isStar = isStar;
    }

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fParamType{" +
				"astProdSubTreeN=" + getAstProdSubTreeN() +
				", isFatArrow=" + isFatArrow +
				", isStar=" + isStar +
				'}';
	}
}
