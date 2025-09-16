package com.flint.compiler.frontend.ast.nodes;

import com.flint.compiler.frontend.ast.nodes.leaves.node.fPackage;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.parse.fCompilationUnit;

public abstract class AstNodVisitor {
	public abstract void visit(fPackage node);
	public abstract void visit(fCaseClauses node);
	public abstract void visit(fType node);
	public abstract void visit(fModifiers node);
	public abstract void visit(fImport node);
	public abstract void visit(fClassDef node);
	public abstract void visit(fObject node);
	public abstract void visit(fTraitDef node);

	public abstract void visit(fIf node);
	public abstract void visit(fParam node);

	public abstract void visit(fWhile node);
	public abstract void visit(fFor node);
	public abstract void visit(fReturn node);
	public abstract void visit(fClassTemplate ct);
	public abstract void visit(fTemplateBody node);
	public abstract void visit(fClassParents node);
	public abstract void visit(fParamType node);
	public abstract void visit(fValueDecl node);
	public abstract void visit(fValueDef node);
	public abstract void visit(fFunSig node);
	public abstract void visit(fClassConstr node);
	public abstract void visit(fTry node);
	public abstract void visit(fGenerator node);
	public abstract void visit(fClassParamClauses node);
	public abstract void visit(fParamTypeList node);
	public abstract void visit(fThrow node);
	public abstract void visit(fThisFun node);
	public abstract void visit(fTypeDef node);
	public abstract void visit(fTypeParam node);
	public abstract void visit(fUnderscore node);
	public abstract void visit(fValue node);
	public abstract void visit(fFun node);
	public abstract void visit(fParamClauses node);
	public abstract void visit(fConstrBlock node);
	public abstract void visit(AstOperatorNod node);
	public abstract void visit(fClassParam node);
	public abstract void visit(fStableId node);
	public abstract void visit(fId node);
	public abstract void visit(fIds node);
	public abstract void visit(fLiteral node);
	public abstract void visit(fTypeArgs node);
	public abstract void visit(fVariantTypeParam node);
	public abstract void visit(fAccessModifier node);
	public abstract void visit(fAccessQualifier node);
	public abstract void visit(fCompilationUnit node);

}
