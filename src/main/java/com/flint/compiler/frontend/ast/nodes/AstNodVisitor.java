package com.flint.compiler.frontend.ast.nodes;

import com.flint.compiler.frontend.ast.nodes.leaves.node.fPackage;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;

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
	public abstract void visit(fTemplateBody node);


}
