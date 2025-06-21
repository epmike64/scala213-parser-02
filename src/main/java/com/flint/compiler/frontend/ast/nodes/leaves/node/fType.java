package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fType extends AstOperandNod {
    public final AstProdSubTreeN astProdSubTreeN;
    public fType(AstProdSubTreeN astProdSubTreeN) {
        this.astProdSubTreeN = astProdSubTreeN;
    }

    @Override
    public fToken getFirstToken() {
        return null;
    }
}
