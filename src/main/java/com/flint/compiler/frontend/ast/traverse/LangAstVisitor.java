package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstSubTreeNod;
import com.flint.compiler.frontend.parse.fCompilationUnit;

public class LangAstVisitor extends AstNodVisitor  {
	private final fCompilationUnit cu;
	public LangAstVisitor(fCompilationUnit cu){
		this.cu = cu;
	}

	public void visit() {
		System.out.println("Visiting Compilation Unit");
		cu.accept(this);
	}

	@Override
	public void visit(fCompilationUnit node) {
		for(fPackage p: node.getPackages()) {
			p.accept(this);
		}

		for(fImport i: node.getImports()) {
			i.accept(this);
		}

		for(AstNod s: node.getStatements()) {
			s.accept(this);
		}
	}

	@Override
	public void visit(fPackage node) {
		System.out.println("Visiting Package: " + node);
	}

	@Override
	public void visit(fImport node) {
		System.out.println("Visiting Import: " + node);
	}

	@Override
	public void visit(fClassDef cls) {
		System.out.println("Visiting Class: " + cls);
		for(fTypeParam tp: cls.getTypeParams()) {
			tp.accept(this);
		}
		if(cls.getConstrAccessModifier() != null) {
			cls.getConstrAccessModifier().accept(this);
		}
		if(cls.getClassParamClauses() != null) {
			cls.getClassParamClauses().accept(this);
		}
		if(cls.getExtendsTemplate() != null) {
			cls.getExtendsTemplate().accept(this);
		}
	}

	@Override
	public void visit(fVariantTypeParam node) {
		System.out.println("Visiting Variant Type Param: " + node);
	}

	@Override
	public void visit(fAccessModifier am) {
		System.out.println("Visiting Access Modifier: " + am);
		if(am.getQualifier() != null) {
			am.getQualifier().accept(this);
		}
	}

	@Override
	public void visit(fAccessQualifier aq) {
		System.out.println("Visiting Access Qualifier: " + aq);
	}

	@Override
	public void visit(fLocalModifier node) {
		System.out.println("Visiting Local Modifier: " + node);
	}

	@Override
	public void visit(fOverrideModifier node) {
		System.out.println("Visiting Override Modifier: " + node);
	}

	@Override
	public void visit(fObject node) {
		System.out.println("Visiting Object: " + node);
	}

	@Override
	public void visit(fCaseClauses node) {
		System.out.println("Visiting Case Clauses" + node);
	}

	@Override
	public void visit(fType node) {
		System.out.println("Visiting Type: " + node);
	}

	@Override
	public void visit(fModifiers node) {
		System.out.println("Visiting Modifiers" + node);
	}



	@Override
	public void visit(fTraitDef node) {
		System.out.println("Visiting Trait: " + node);
	}

	@Override
	public void visit(fIf node) {
		System.out.println("Visiting If Node" + node);
	}

	@Override
	public void visit(fParam node) {
		System.out.println("Visiting Param: " + node);
	}

	@Override
	public void visit(fWhile node) {
		System.out.println("Visiting While Node" + node);
	}

	@Override
	public void visit(fFor node) {
		System.out.println("Visiting For Node" + node);
	}

	@Override
	public void visit(fReturn node) {
		System.out.println("Visiting Return Node" + node);
	}

	@Override
	public void visit(fClassTemplate ct) {
		System.out.println("Visiting Class Template" + ct);
		ct.getParents().accept(this);
	}

	@Override
	public void visit(fTemplateBody tb) {
		System.out.println("Visiting Template Body" + tb);
		for(AstNod s: tb.getStmts()) {
			s.accept(this);
		}
	}

	@Override
	public void visit(fClassParents node) {
		System.out.println("Visiting Class Parents" + node);
	}

	@Override
	public void visit(fParamType node) {
		System.out.println("Visiting Param Type: " + node);
	}

	@Override
	public void visit(fValueDecl node) {
		System.out.println("Visiting Value Decl: " + node);
	}

	@Override
	public void visit(fValueDef node) {
		System.out.println("Visiting Value Def: " + node);
		node.getModifiers().accept(this);
		for(AstProdSubTreeN name: node.getNames()) {
			name.accept(this);
		}
		if(node.getType() != null) {
			node.getType().accept(this);
		}
		if(node.getAssignExpr() != null) {
			node.getAssignExpr().accept(this);
		}
	}

	@Override
	public void visit(fFunSig s) {
		System.out.println("Visiting Fun Sig: " + s);
		s.getName();
		if(s.getTypeParams() != null){
			for(fTypeParam tp: s.getTypeParams()) {
				tp.accept(this);
			}
		}

		if(s.getParamClauses() != null) {
			s.getParamClauses().accept(this);
		}
	}

	@Override
	public void visit(fClassConstr cc) {
		System.out.println("Visiting Class Constructor" + cc);
		cc.getParamType().accept(this);
		if(cc.getArgs() != null) {
			cc.getArgs().accept(this);
		}
	}

	@Override
	public void visit(fTry node) {
		System.out.println("Visiting Try Node" + node);
	}

	@Override
	public void visit(fGenerator node) {
		System.out.println("Visiting Generator Node");
	}

	@Override
	public void visit(fClassParamClauses node) {
		System.out.println("Visiting Class Param Clauses" + node);
	}

	@Override
	public void visit(fParamTypes node) {
		System.out.println("Visiting Param Type List" + node);
	}

	@Override
	public void visit(fThrow node) {
		System.out.println("Visiting Throw Node" + node);
	}

	@Override
	public void visit(fThisFun node) {
		System.out.println("Visiting This Fun Node" + node);
	}

	@Override
	public void visit(fTypeDef node) {
		System.out.println("Visiting Type Def: " + node);
	}

	@Override
	public void visit(fTypeParam node) {
		System.out.println("Visiting Type Param: " + node);
	}

	@Override
	public void visit(fUnderscore node) {
		System.out.println("Visiting Underscore Node" + node);
	}

	@Override
	public void visit(fValue node) {
		System.out.println("Visiting Value Node" + node);
	}

	@Override
	public void visit(fParamClauses node) {
		System.out.println("Visiting Param Clauses" + node);
	}

	@Override
	public void visit(fConstrBlock node) {
		System.out.println("Visiting Constructor Block" + node);
	}

	@Override
	public void visit(AstOperatorNod node) {
		System.out.println("Visiting Operator Node: " + node.getLangOperatorKind());
		if(node.getAstLeftN() != null) {
			node.getAstLeftN().accept(this);
		}
		if(node.getAstRightN() != null) {
			node.getAstRightN().accept(this);
		}
	}

	@Override
	public void visit(fClassParam node) {
		System.out.println("Visiting Class Param: " + node);
	}

	@Override
	public void visit(fStableId node) {
		System.out.println("Visiting StableId: " + node);
	}

	@Override
	public void visit(fId node) {
		System.out.println("Visiting Id: " + node);
	}

	@Override
	public void visit(fIds node) {
		System.out.println("Visiting Ids: " + node);
	}

	@Override
	public void visit(fLiteral node) {
		System.out.println("Visiting Literal: " + node);
	}

	@Override
	public void visit(fTypeArgs node) {
		System.out.println("Visiting Type Args");
	}

	@Override
	public void visit(fNamedFun f) {
		System.out.println("Visiting Named Fun: " + f);
		f.getFunSig().accept(this);
		if(f.getReturnType() != null) {
			f.getReturnType().accept(this);
		}
		if(f.getBody() != null) {
			f.getBody().accept(this);
		}
	}

	@Override
	public void visit(fBlock node) {
		System.out.println("Visiting Block Node");
		for(AstNod s: node.getStatements()) {
			s.accept(this);
		}
	}

	@Override
	public void visit(AstSubTreeNod node) {
		System.out.println("Visiting SubTree Node");
	}

	@Override
	public void visit(AstProdSubTreeN node) {
		System.out.println("Visiting Prod SubTree Node");
		if(node.rootOpNod.getAstLeftN() != null) {
			node.rootOpNod.getAstLeftN().accept(this);
		}
		if(node.rootOpNod.getAstRightN() != null) {
			node.rootOpNod.getAstRightN().accept(this);
		}
	}
}
