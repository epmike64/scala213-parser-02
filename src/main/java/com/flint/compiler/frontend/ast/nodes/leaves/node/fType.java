package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public class fType extends AstOperandNod {
    private final AstProdSubTreeN astProdSubTreeN;
    public fType(AstProdSubTreeN astProdSubTreeN) {
        this.astProdSubTreeN = astProdSubTreeN;
    }

	public AstProdSubTreeN getAstProdSubTreeN()	 {
		return astProdSubTreeN;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fType{" +
				"astProdSubTreeN=" + astProdSubTreeN +
				'}';
	}
}
