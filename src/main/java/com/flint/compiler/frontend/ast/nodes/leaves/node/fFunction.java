package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fFunction extends AstOperandNod {
    private final AstOperandNod funName;
    private final AstProdSubTreeN funArgs;

    public fFunction(AstOperandNod funName, AstProdSubTreeN funArgs) {
        this.funName = funName;
        this.funArgs = funArgs;
    }
}
