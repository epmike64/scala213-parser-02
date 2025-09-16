package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.parse.fCompilationUnit;

public class LangAstVisitor extends AstNodVisitor  {
	private final fCompilationUnit cu;
	public LangAstVisitor(fCompilationUnit cu){
		this.cu = cu;
	}

	public void visit() {
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

	}

	@Override
	public void visit(fImport node) {

	}

	@Override
	public void visit(fClassDef cls) {
		cls.getName();
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

	}

	@Override
	public void visit(fAccessModifier am) {
		am.getKind();
		if(am.getQualifier() != null) {
			am.getQualifier().accept(this);
		}
	}

	@Override
	public void visit(fAccessQualifier aq) {
		aq.getKind();
		if(aq.getKind() == fAccessQualifier.Kind.ID) {
			aq.getId();
		}
	}

	@Override
	public void visit(fObject node) {

	}

	@Override
	public void visit(fCaseClauses node) {

	}

	@Override
	public void visit(fType node) {

	}

	@Override
	public void visit(fModifiers node) {

	}



	@Override
	public void visit(fTraitDef node) {

	}

	@Override
	public void visit(fIf node) {

	}

	@Override
	public void visit(fParam node) {

	}

	@Override
	public void visit(fWhile node) {

	}

	@Override
	public void visit(fFor node) {

	}

	@Override
	public void visit(fReturn node) {

	}

	@Override
	public void visit(fClassTemplate ct) {
		ct.getParents().accept(this);
	}

	@Override
	public void visit(fTemplateBody node) {

	}

	@Override
	public void visit(fClassParents node) {

	}

	@Override
	public void visit(fParamType node) {

	}

	@Override
	public void visit(fValueDecl node) {

	}

	@Override
	public void visit(fValueDef node) {

	}

	@Override
	public void visit(fFunSig node) {

	}

	@Override
	public void visit(fClassConstr cc) {
		cc.getParamType().accept(this);
		if(cc.getArgs() != null) {
			cc.getArgs().accept(this);
		}
	}

	@Override
	public void visit(fTry node) {

	}

	@Override
	public void visit(fGenerator node) {

	}

	@Override
	public void visit(fClassParamClauses node) {

	}

	@Override
	public void visit(fParamTypeList node) {

	}

	@Override
	public void visit(fThrow node) {

	}

	@Override
	public void visit(fThisFun node) {

	}

	@Override
	public void visit(fTypeDef node) {

	}

	@Override
	public void visit(fTypeParam node) {

	}

	@Override
	public void visit(fUnderscore node) {

	}

	@Override
	public void visit(fValue node) {

	}

	@Override
	public void visit(fFun node) {

	}

	@Override
	public void visit(fParamClauses node) {

	}

	@Override
	public void visit(fConstrBlock node) {

	}

	@Override
	public void visit(AstOperatorNod node) {

	}

	@Override
	public void visit(fClassParam node) {

	}

	@Override
	public void visit(fStableId node) {

	}

	@Override
	public void visit(fId node) {

	}

	@Override
	public void visit(fIds node) {

	}

	@Override
	public void visit(fLiteral node) {

	}

	@Override
	public void visit(fTypeArgs node) {

	}


}
