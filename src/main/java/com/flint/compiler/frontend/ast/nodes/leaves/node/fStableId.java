package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

import java.util.ArrayList;
import java.util.List;

public class fStableId extends AstOperandNod {

    public static class fTPair {
        final fToken id;
        final fTokenKind kind;
         fTPair(fToken id, fTokenKind kind) {
               this.id = id;
               this.kind = kind;
         }
    }

    private final boolean isPath;

    public fStableId(boolean isPath) {
         this.isPath = isPath;
    }

    private final List<fTPair> tokens = new ArrayList<>();

    public void addTId(NamedToken tId) {
        tokens.add(new fTPair(tId, fTokenKind.T_ID));
    }

    public void addSuper(fToken s){
        assert s.kind == fTokenKind.T_SUPER;
        tokens.add(new fTPair(null, fTokenKind.T_SUPER));
    }

      public void addThis(fToken t) {
      assert t.kind == fTokenKind.T_THIS;
        tokens.add(new fTPair(null, fTokenKind.T_THIS));
      }

      public void addClassQualifier(NamedToken tId) {
         tokens.add(new fTPair(tId, fTokenKind.T_CLASS_QUALIFIER));
      }

      public fTokenKind getLastTKind() {
         if (tokens.isEmpty()) {
            return null;
         }
         return tokens.get(tokens.size() - 1).kind;
      }
}