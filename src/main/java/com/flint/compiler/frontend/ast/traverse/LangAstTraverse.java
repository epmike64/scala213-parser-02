package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fImport;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fPackage;
import com.flint.compiler.frontend.parse.fCompilationUnit;

public class LangAstTraverse extends AstTraverse{
	private final fCompilationUnit cu;

	public LangAstTraverse(fCompilationUnit cu){
		this.cu = cu;
	}

	public void traverse(){
		System.out.printf("Traversing AST for Compilation Unit:%n");

		for(fPackage pkg : cu.getPackages()){
			System.out.printf("Package: %s%n", pkg);
		}

		for(fImport imp : cu.getImports()){
			System.out.printf("Import: %s%n", imp);
		}

		for(AstNod stmt : cu.getStatements()){
			System.out.printf("Statement: %s%n", stmt);
			if(stmt instanceof AstOperandNod){
				postOrder((AstOperandNod) stmt);
			}else{
				postOrder(stmt);
			}
		}
	}
}
