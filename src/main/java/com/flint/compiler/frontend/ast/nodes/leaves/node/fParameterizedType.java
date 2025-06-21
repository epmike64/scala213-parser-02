package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fParameterizedType extends fType{
    private final AstProdSubTreeN typeArgs;
    private final AstOperatorNod typeName;
    public fParameterizedType(AstOperandNod typeName, AstProdSubTreeN typeArgs) {
        super(null);
        this.typeName = null;// (AstOperatorNod) typeName;
        this.typeArgs = typeArgs;
    }
}
