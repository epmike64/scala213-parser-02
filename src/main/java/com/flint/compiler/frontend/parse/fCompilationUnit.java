package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;

public class fCompilationUnit {
	private final AstProdSubTreeN compilationUnit;
	public fCompilationUnit(AstProdSubTreeN compilationUnit) {
		this.compilationUnit = compilationUnit;
	}
	public AstProdSubTreeN getCompilationUnit() {
		return compilationUnit;
	}
}
