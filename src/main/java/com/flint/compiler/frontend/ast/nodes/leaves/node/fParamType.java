package com.flint.compiler.frontend.ast.nodes.leaves.node;

public class fParamType extends fType{
    public final boolean isFatArrow, isStar;

    public fParamType(fType t, boolean isFatArrow, boolean isStar) {
        super(t.astProdSubTreeN);
        this.isFatArrow = isFatArrow;
        this.isStar = isStar;
    }
}
