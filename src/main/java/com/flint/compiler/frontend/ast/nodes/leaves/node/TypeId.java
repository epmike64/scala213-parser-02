package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class TypeId extends AstOperandNod {
    public final StableId id;
    public final fType type;

    public TypeId(StableId id, fType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public fToken getFirstToken() {
        return id.getFirstToken();
    }
}
