package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.parse.fCompilationUnit;

public class LangAstTraverse extends AstTraverse{
	private final fCompilationUnit cu;

	public LangAstTraverse(fCompilationUnit cu){
		this.cu = cu;
	}

	public void traverse(){
		System.out.printf("Traversing AST for Compilation Unit:%n");
		postOrder(cu.getCompilationUnit());
	}
}
