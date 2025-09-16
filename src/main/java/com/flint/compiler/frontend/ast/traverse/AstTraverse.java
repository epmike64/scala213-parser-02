package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.parse.utils.Ast;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class AstTraverse {

	void traverse(AstProdSubTreeN a){
		postOrder(a.rootOpNod);
	}

	void traverse(Ast a){
		postOrder(a.rootOp);
	}

	void postOrder(AstProdSubTreeN tree){
		postOrder(tree.rootOpNod);
	}

	void postOrder(AstNod node) {
		if (node == null) return;
		postOrder(node.getAstLeftN());
		postOrder(node.getAstRightN());
		System.out.println(node);
	}
}
