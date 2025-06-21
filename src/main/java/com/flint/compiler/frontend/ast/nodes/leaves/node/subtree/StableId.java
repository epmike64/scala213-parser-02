package com.flint.compiler.frontend.ast.nodes.leaves.node.subtree;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class StableId extends AstOperandNod {
    private List<fToken> isTokens;

    public StableId(List<fToken> isTokens) {
        this.isTokens = isTokens;
    }

    public boolean isSimplified() {
        return isTokens.size() == 1;
    }

    @Override
    public fToken getFirstToken() {
        return isTokens.isEmpty() ? null : isTokens.get(0);
    }
}
