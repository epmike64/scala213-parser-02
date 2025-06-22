package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.List;

public class fPath extends StableId {
    public fPath(List<fToken> idTokens){
        super(idTokens);
    }
}
