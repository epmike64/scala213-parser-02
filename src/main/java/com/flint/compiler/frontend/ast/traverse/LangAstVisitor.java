package com.flint.compiler.frontend.ast.traverse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fCompilationUnit;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;

import java.util.List;

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
		System.out.println(cls.getName());
		if(cls.getModifiers() != null) {
			cls.getModifiers().accept(this);
		}
		if(cls.getConstrAccessModifier() != null) {
			cls.getConstrAccessModifier().accept(this);
		}
		if(cls.getTypeParams() != null) {
			for (fTypeParam tp : cls.getTypeParams()) {
				tp.accept(this);
			}
		}
		if(cls.getClassParamClauses() != null) {
			cls.getClassParamClauses().accept(this);
		}
		if(cls.getExtendsTemplate() != null) {
			cls.getExtendsTemplate().accept(this);
		}
	}

	@Override
	public void visit(fVariantTypeParam p) {
		System.out.println(p.getName());
		if(p.getType() != null) {
			p.getType().accept(this);
		}
		if(p.getUpperBound() != null) {
			p.getUpperBound().accept(this);
		}
		if(p.getLowerBound() != null) {
			p.getLowerBound().accept(this);
		}
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
	public void visit(fLocalModifier m) {
		System.out.println("Visiting Local Modifier: " + m.getModifierType());
	}

	@Override
	public void visit(fOverrideModifier node) {
		System.out.println("Visiting Override Modifier: ");
	}

	@Override
	public void visit(fObject o) {
		if(o.getModifiers() != null) {
			o.getModifiers().accept(this);
		}
		if(o.getExtendsTemplate() != null) {
			o.getExtendsTemplate().accept(this);
		}
	}

	@Override
	public void visit(fCaseClauses cc) {
		for(fCaseClause c: cc.getCaseClauses()) {
			c.accept(this);
		}
	}

	@Override
	public void visit(fType t) {
		t.getAstProdSubTreeN().accept(this);
	}

	@Override
	public void visit(fModifiers m) {

		if(m.getAccessModifier() != null){
			m.getAccessModifier().accept(this);
		}
		if(m.getOverrideModifier() != null){
			m.getOverrideModifier().accept(this);
		}
		if(m.getLocalModifier() != null){
			m.getLocalModifier().accept(this);
		}
	}



	@Override
	public void visit(fTraitDef t) {
		System.out.println(t.getName());
		if(t.getModifiers() != null) {
			t.getModifiers().accept(this);
		}
		if(t.getTypeParams() != null) {
			for (fTypeParam tp : t.getTypeParams()) {
				tp.accept(this);
			}
		}
		if(t.getExtendsTemplate() != null) {
			t.getExtendsTemplate().accept(this);
		}

	}

	@Override
	public void visit(fIf f) {
		System.out.println("Visiting If Node" + f);
		f.getCondExpr().accept(this);
		f.getIfBody().accept(this);
		if(f.getElseBody() != null) {
			f.getElseBody().accept(this);
		}
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
	public void visit(fFor ff) {
		System.out.println("Visiting For Node" + ff);
		for(fGenerator g: ff.getGenerators()) {
			g.accept(this);
		}
		ff.getYieldExpr().accept(this);
	}

	@Override
	public void visit(fReturn node) {
		System.out.println("Visiting Return Node" + node);
	}

	@Override
	public void visit(fClassTemplate ct) {
		System.out.println("Visiting Class Template" + ct);
		ct.getParents().accept(this);
		if(ct.getTemplateBody() != null) {
			ct.getTemplateBody().accept(this);
		}
	}

	@Override
	public void visit(fTemplateBody tb) {
		System.out.println("Visiting Template Body" + tb);
		for(AstNod s: tb.getStmts()) {
			s.accept(this);
		}
	}

	@Override
	public void visit(fClassParents cp) {
		System.out.println("Visiting Class Parents " + cp);
		cp.getConstr().accept(this);

		for(fParamType pt: cp.getWithTypes()) {
			pt.accept(this);
		}

	}

	@Override
	public void visit(fParamType pt) {
		System.out.println("Visiting Param Type: " + pt);
		pt.getAstProdSubTreeN().accept(this);
	}

	@Override
	public void visit(fValueDecl vd) {
		System.out.println("Visiting Value Decl: " + vd);
	}

	@Override
	public void visit(fValueDef node) {
		System.out.println("Visiting Value Def: " + node);
		if(node.getModifiers() != null) {
			node.getModifiers().accept(this);
		}
		for(AstProdSubTreeN name: node.getNames()) {
			name.accept(this);
		}
		if(node.getType().isPresent()) {
			node.getType().get().accept(this);
		}
		if(node.getAssignExpr().isPresent()) {
			node.getAssignExpr().get().accept(this);
		}
	}

	@Override
	public void visit(fFunSig s) {
		System.out.println("Visiting Fun Sig: " + s);

		if (s.getTypeParams() != null) {
			for (fTypeParam tp : s.getTypeParams()) {
				tp.accept(this);
			}
		}

		if(s.getParamClauses() != null) {
			s.getParamClauses().accept(this);
		}
	}

	@Override
	public void visit(fClassConstr cc) {
		System.out.println("Visiting Class Constructor  " + cc);
		cc.getParamType().accept(this);

		if(cc.getArgs() != null) {
			cc.getArgs().accept(this);
		}
	}

	@Override
	public void visit(fTry node) {
		System.out.println("Visiting Try Node " + node);
	}

	int gcount = 0;
	@Override
	public void visit(fGenerator g) {
		System.out.println("GCount: "+ (++gcount)+ "Visiting Generator Node " + g);
		g.getCasePattern1().accept(this);
		g.getInExpr().accept(this);

		for(AstProdSubTreeN guard: g.getGuards()) {
			guard.accept(this);
		}

		assert g.getEndingPattern1s().size() == g.getEndingExprs().size();
		for(int i = 0; i < g.getEndingPattern1s().size(); i++) {
			g.getEndingPattern1s().get(i).accept(this);
			g.getEndingExprs().get(i).accept(this);
		}
	}

	@Override
	public void visit(fClassParamClauses cps) {
		System.out.println("Visiting Class Param Clauses " + cps);

		for(List<fClassParam> cpList : cps.getParams()){
			for(fClassParam cp : cpList) {
				cp.accept(this);
			}
		}

		if(cps.getImplicitParams() != null) {
			for(fClassParam cp : cps.getImplicitParams()){
				cp.accept(this);
			}
		}
	}

	@Override
	public void visit(fParamTypes node) {
		System.out.println("Visiting Param Type List " + node);
		for(fParamType pt: node.getParamTypes()) {
			pt.accept(this);
		}
	}

	@Override
	public void visit(fThrow node) {
		System.out.println("Visiting Throw Node" + node);
	}

	@Override
	public void visit(fThisFun f) {
		System.out.println("Function is this");
		f.getParamClauses().accept(this);
		f.getConstrBlock().accept(this);
	}

	@Override
	public void visit(fTypeDef t) {
		System.out.println(t.getName());
		if(t.getTypeParams() != null) {
			for(fVariantTypeParam tp: t.getTypeParams()) {
				tp.accept(this);
			}
		}
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
	public void visit(fParamClauses c) {

		for(List<fParam> pList: c.getParams()) {
			for(fParam p: pList) {
				p.accept(this);
			}
		}

		if(c.getImplicitParams() != null) {
			for(fParam p: c.getImplicitParams()) {
				p.accept(this);
			}
		}
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
	public void visit(fClassParam cp) {
		if(cp.getModifiers() != null) {
			cp.getModifiers().accept(this);
		}
//		cp.getIdentifier();
//		cp.getMutability();
		if(cp.getParamType() != null) {
			cp.getParamType().accept(this);
		}
		if(cp.getDefaultValue() != null) {
			cp.getDefaultValue().accept(this);
		}

	}

	@Override
	public void visit(fStableId node) {
		System.out.println("Visiting Stable Id: " + node);
	}

	@Override
	public void visit(fId node) {
		System.out.println("Visiting Id: " + node);
	}

	@Override
	public void visit(fIds node) {
		for(fNameValToken id: node.getIds()) {
			System.out.print(id );
		}
		System.out.println();
	}

	@Override
	public void visit(fLiteral node) {
		System.out.println(node);
	}

	@Override
	public void visit(fTypeArgs node) {

		for(fType t: node.getTypeArgs()) {
			t.accept(this);
		}
	}

	@Override
	public void visit(fNamedFun f) {
		f.getFunSig().accept(this);
		if(f.getReturnType().isPresent()) {
			f.getReturnType().get().accept(this);
		}
		if(f.getBody().isPresent()) {
			f.getBody().get().accept(this);
		}
	}

	@Override
	public void visit(fBlock node) {


		for(AstNod s: node.getStmts()) {
			s.accept(this);
		}

	}

	@Override
	public void visit(AstProdSubTreeN node) {

		assert node.getRootOpNod() != null;
		assert node.getRootOpNod().getAstLeftN() == null;
		if(node.getRootOpNod().getAstRightN() != null) {
			node.getRootOpNod().getAstRightN().accept(this);
		}
	}

	@Override
	public void visit(fTemplate node) {

		if(node.getTemplateBody() != null) {
			node.getTemplateBody().accept(this);
		}
	}

	@Override
	public void visit(fCaseClause cc) {
		cc.getPattern().accept(this);
		if(cc.getGuard() != null) {
			cc.getGuard().accept(this);
		}
		cc.getBlock().accept(this);
	}
}
