package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.*;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.OpChar;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.utils.Ast;

import java.util.ArrayList;
import java.util.List;

import static com.flint.compiler.frontend.parse.lex.token.fTokenKind.*;

public class fParser {

	private final ParseHelp h;

	public fParser(fLexer lexer) {
		h = new ParseHelp(lexer);
	}

	void skipSemi() {
		if(h.isTkSemicolon()){
			h.next();
			return;
		}
		while (h.isTkNL()){
			h.next();
		}
	}

	List<NamedToken> ids() {
		assert h.TKnd() == fTokenKind.T_ID;
		List<NamedToken> ids = new ArrayList<>();
		while (h.TKnd() == T_ID) {
			ids.add((NamedToken) h.next());
		}
		return ids;
	}

	fParamType paramType(boolean isSimpleType) {
		boolean isFatArrow = false;
		boolean isStar = false;
		if(!isSimpleType){
			if(h.isLa(0, fTokenKind.T_FAT_ARROW)){
				isFatArrow = true; h.next();
			}
		}
		fType t = type();
		if(!isSimpleType){
			if(h.isStarOpT(0)){
				isStar = true; h.next();
			}
		}
		return new fParamType(t, isFatArrow, isStar);
	}

	fParamType simpleType() {
		Ast a = new Ast();
		loop:
		while(true) {
			switch (h.TKnd()) {
				case T_LPAREN: {// (Type, Type)
					typeLParen(a, true);
					continue;
				}
				case T_ID: case T_SUPER: case T_THIS: {
					typeTID(a);
					continue;
				}
				default:
					break loop;
			}
		}
		return new fParamType(new fType(new AstProdSubTreeN(GrmPrd.SIMPLE_TYPE, a)), false, false);
	}

	fType type() {
		Ast a = new Ast();
		loop:
		while(true) {
			switch(h.TKnd()){
				case T_FAT_ARROW:{
					typeFatArrow(a);
					break loop;
				}
				case T_LPAREN: {//(ParamType, ParamType) | (Type, Type)
					typeLParen(a, false);
					continue;
				}
				case T_LBRACKET: {//typeArgs
					typeLBracket(a);
					continue;
				}
				case T_ID: case T_SUPER: case T_THIS:
					typeTID(a);
					continue;
				case T_WITH:
					typeWith(a);
					continue;
				default:
					break loop;
			}
		}
		return new fType(new AstProdSubTreeN(GrmPrd.TYPE, a));
	}

	void typeWith(Ast a) {
		while(h.isTkWith()) {
			switch (a.astLastNKnd()) {
				case AST_OPERAND: {
					h.insertOperator(a, fLangOperatorKind.O_WITH,  h.next());
					a.setRight(type());
					continue ;
				}
				default:
					throw new RuntimeException("With in unexpected place: " + a.astLastNKnd());
			}
		}
	}

	AstProdSubTreeN types() {
		Ast a = new Ast();
		while(true){
			a.setRight(type());
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.TYPES, a);
	}

	List<fParamType> paramTypes(boolean isSimpleType) {
		List<fParamType> ps = new ArrayList<>();
		Ast a = new Ast();
		while(true){
			ps.add(paramType(isSimpleType));
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return ps;
	}

	void typeFatArrow(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW,  h.next());
				a.setRight(type());
				break;
			}
			default:
				throw new RuntimeException("FatArrow in unexpected place: " + a.astLastNKnd());
		}
	}

	void typeLBracket(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.accept(fTokenKind.T_LBRACKET);
				h.insertOperator(a, fLangOperatorKind.O_BRACKETS,  h.next());
				a.setRight(types());
				h.accept(T_RBRACKET);
			}
			default:
				throw new RuntimeException("LBracket in unexpected place: " + a.astLastNKnd());
		}
	}

	List<fParamType> typeLParen(Ast a, boolean isSimpleType) {
		List<fParamType> ps;
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				h.accept(fTokenKind.T_LPAREN);
				ps = paramTypes(isSimpleType);
				h.accept(fTokenKind.T_RPAREN);
				return ps;
			}
			default:
				throw new RuntimeException("LParen in unexpected place: " + a.astLastNKnd());
		}
	}

	AstProdSubTreeN expr(Ast a) {
		if(a == null) a = new Ast();
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN:{
					break loop;
				}
				case T_DOT: {
					h.next();
					h.acceptOpChar(OpChar.ASSIGN);
					//expr()
					break loop;
				}
				case T_LBRACKET: {
					exprLBracket(a);
					break loop;
				}
				case T_MATCH:{
					h.next();
					h.accept(fTokenKind.T_LCURL);
					caseClauses(a);
					h.accept(fTokenKind.T_RCURL);
					break loop;
				}
				case T_RPAREN: {
					break loop;
				}
				case T_LPAREN: {
					exprLParen(a);
					if(h.isLa(0, fTokenKind.T_FAT_ARROW)){
						h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, h.next());
						a.setRight(expr(null));
						break loop;
					}
					continue ;
				}
				case T_NEW: {
					a.setRight(exprNEW());
					continue;
				}
				case T_LCURL: {
					exprLCURL(a);
					continue;
				}
				case T_ID: case T_THIS: case T_SUPER: {
					exprTID(a);
					if(a.isContinue()) continue;
					break loop;
				}
				default:
					throw new AssertionError("Unexpected token: " + h.getToken());
			}
		}

		return new AstProdSubTreeN(GrmPrd.EXPR, a);
	}

	fStableId path(){
		return stableId(true);
	}

	fStableId stableId(boolean isPath) {
		assert h.TKnd() == fTokenKind.T_ID || h.TKnd() == fTokenKind.T_THIS || h.TKnd() == fTokenKind.T_SUPER
				: "Expected T_ID, T_THIS or T_SUPER but found: " + h.getToken();

		fStableId sid = new fStableId(isPath);
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_DOT: {
					if(h.isLa(1, T_ID, T_SUPER, T_THIS)){
						h.next();
						continue;
					}
					break loop;
				}
				case T_ID: {
					sid.addTId((NamedToken) h.next());
					continue;
				}
				case T_THIS: {
					sid.addThis(h.next());
					continue;
				}
				case T_SUPER: {
					sid.addSuper(h.next());
					h.next();
					if (h.isTkLBracket()) {
						h.next();
						sid.addClassQualifier((NamedToken) h.accept(T_ID));
						h.accept(T_RBRACKET);
					}
					continue;
				}
				default:
					break;
			}
		}
		fTokenKind last = sid.getLastTKind();
		assert last == fTokenKind.T_ID;
		if(isPath){
			 assert last == fTokenKind.T_THIS;
		}
		return sid;
	}


	void typeTID(Ast a){
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(stableId(false));
				break;
			}
			case AST_OPERAND:{
				/* ID OPERATOR */
				if(h.isPoundOpT(0)){
					h.insertOperator(a, fLangOperatorKind.O_POUND, h.next());
					a.setRight(new fStableId(false));
				} else {
					h.insertOperator(a, fLangOperatorKind.O_ID_SMBLC_RIGHT_ASSC,  h.next());
				}
				break;
			}
			default:
				throw new RuntimeException("TID in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprTID(Ast a) {
		switch(a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				/* ID OPERAND */
				a.setRight(path());
				break;
			}
			case AST_OPERAND:{
				/* ID OPERATOR */
				if(h.isColonOpT(0)){
					h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
					a.setRight(type());
					a.setContinue(false);
				}
				else if(h.isAssignOpT(0)) {
					h.insertOperator(a, fLangOperatorKind.O_ASSIGN, h.next());
					a.setRight(expr(a));
					a.setContinue(false);
				} else {
					h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), h.next());
				}
				break;
			}
			default:
				throw new AssertionError("TID in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLParen(Ast a) {
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(exprs());
				break;
			}
			case AST_OPERAND: {
				AstOperandNod operand = (AstOperandNod) a.astLastN();
				a.setRight(new fFunction(operand, exprs()));
				break;
			}
			default:
				throw new RuntimeException("LParen in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLBracket(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				AstOperandNod operand = (AstOperandNod) a.astLastN();
				a.setRight(new fParameterizedType(operand, typeArgs()));
			}
			default:
				throw new RuntimeException("LBracket in unexpected place: " + a.astLastNKnd());
		}
	}

	fTemplateBody exprNEW(){
		h.accept(fTokenKind.T_NEW);
		switch (h.TKnd()){
			case T_LCURL: {
				return templateBody();
			}
			case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: {
				return classTemplate(false);
			}
			default:
				throw new RuntimeException("NEW in unexpected place: " + h.getToken());
		}
	}


	fClassTemplate classTemplate(boolean isTrait) {
		fClassParents cp = classParents(isTrait);
		fTemplateBody tb = null;
		if(h.isTkLCurl()){
			tb = templateBody();
		}
		return new fClassTemplate(tb, cp, isTrait);
	}

	AstProdSubTreeN block() {
		Ast a = new Ast();
		while(true){
			a.setRight(blockStat());
			if(h.isTkSemi()){
				h.next(); continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.BLOCK, a);
	}

	fTemplateBody templateBody() {
		h.accept(fTokenKind.T_LCURL);
		Ast a = new Ast();
		while(true){
			a.setRight(templateStat());
			if(h.isTkSemi()){
				h.next(); continue;
			}
			break;
		}
		h.accept(fTokenKind.T_RCURL);
		return new fTemplateBody(new AstProdSubTreeN(GrmPrd.TEMPLATE_BODY, a));
	}

	AstProdSubTreeN templateStat(){
		return blockOrTemplateStat(GrmPrd.TEMPLATE_STAT);
	}

	AstProdSubTreeN blockStat(){
		return blockOrTemplateStat(GrmPrd.BLOCK_STAT);
	}




	AstOperandNod classObjectDef(){
		boolean isCase = false;
		if(h.isLa(0, fTokenKind.T_CASE)){
			h.next();
			isCase = true;
		}
		switch (h.TKnd()){
			case T_CLASS: {
				return classDef(isCase);
			}
			case T_OBJECT: {
				return objectDef(isCase);
			}
			default:
				throw new RuntimeException("Expected 'class' or 'object' but found: " + h.getToken());
		}
	}

	fClassParamClauses classParamClauses() {
		fClassParamClauses cpcs = new fClassParamClauses();
		while(h.isTkLParen()) {
			if(h.isLa(1, fTokenKind.T_IMPLICIT)){
				cpcs.setImplicitParams(classParamClause(true));
				break;
			}
			cpcs.addParams(classParamClause(false));
		}
		return cpcs;
	}

	List<fClassParam> classParamClause(boolean isImplicit) {
		List<fClassParam> params = new ArrayList<>();
		h.accept(T_LPAREN);
		h.accept(T_IMPLICIT);
		if(!h.isTkRParen()) {
			while (true) {
				params.add(classParam());
				if (h.isTkComma()) {
					h.next();
					continue;
				}
				break;
			}
		}
		h.accept(T_RPAREN);
		return params;
	}

	fClassParam classParam() {
		fClassParam p = new fClassParam();
		//Modifier
		switch (h.TKnd()){
			case T_VAL: {
				p.setValVar(fValVar.VAL);
				break;
			}
			case T_VAR: {
				p.setValVar(fValVar.VAR);
				break;
			}
			default:
				p.setValVar(fValVar.NONE);
				break;
		}
		p.setIdentifier((NamedToken) h.next());
		h.acceptOpChar(OpChar.COLON);
		p.setParamType(paramType(false));
		if(h.isAssignOpT(0)){
			h.next();
			p.setDefaultValue(expr(null));
		}
		return p;
	}

	fClassParents classParents(boolean isTrait) {
		fClassParents parents = new fClassParents(classConstructor(isTrait));
		while(h.isTkWith()) {
			h.next();
			parents.addWithType(simpleType());
		}
		return parents;
	}

	fClassConstructor classConstructor(boolean isTrait) {
		fClassConstructor cc = new fClassConstructor(simpleType());
		if(!isTrait && h.isTkLParen()){
			h.next();
			if(!h.isTkRParen()){
				cc.setArgs(exprs());
			}
			h.accept(T_LPAREN);
		}
		return cc;
	}

	fTraitDef traitDef(){
		h.accept(fTokenKind.T_TRAIT);
		fTraitDef trait = new fTraitDef((NamedToken) h.next());
		if(h.isTkLBracket()){
			trait.setTypeParams(variantTypeParams());
		}
		trait.setExtendsTemplate(classExtends(true));
		return trait;
	}

	fClassDef classDef(boolean isCase) {
		h.accept(fTokenKind.T_CLASS);
		fClassDef cls = new fClassDef((NamedToken) h.next(), isCase);
		if(h.isTkLBracket()){
			cls.setTypeParams(variantTypeParams());
		}
		cls.setClassParamClauses(classParamClauses());
		cls.setExtendsTemplate(classExtends(false));
		return cls;
	}

	fTemplateBody classExtends(boolean isTrait) {
		switch (h.TKnd()){
			case T_LCURL: {
				return templateBody();
			}
			case T_EXTENDS: {
				h.next();
				if(h.isLa(0, fTokenKind.T_LCURL)){
					return templateBody();
				} else {
					return classTemplate(isTrait);
				}
			}
			default:
		}
		return null;
	}

	fObject objectDef(boolean isCase) {
		h.accept(fTokenKind.T_OBJECT);
		fObject obj = new fObject((NamedToken) h.next(), isCase);
		obj.setExtendsTemplate(classExtends(false));
		return obj;
	}

	fFunctionDef  funDef(){
		h.accept(fTokenKind.T_DEF);
		fFunctionDef fun = null;
		if(h.isLa(0, fTokenKind.T_ID)){
			fun = new fFunctionDef((NamedToken) h.next());
			if(h.isTkLBracket()){
				fun.setTypeParams(funTypeParams());
			}
			fun.setParamClauses(paramClauses());
			if(h.isColonOpT(0)){
				h.next();
				fun.setReturnType(type());
				h.acceptOpChar(OpChar.ASSIGN);
				fun.setBody(expr(null));
			} else if(h.isAssignOpT(0)){
				fun.setBody(expr(null));
			} else if(h.isTkLCurl()){
				h.next();
				fun.setBody(block());
				h.accept(fTokenKind.T_RCURL);
			} else {
				throw new RuntimeException("Unexpected token: " + h.getToken());
			}
		} else if(h.isLa(0, fTokenKind.T_THIS)){
			/*
			   'this'  paramClause()  paramClasses() ('=' ConstrExpr | ConstrBlock)
			 */

		} else {
			throw new AssertionError("Unexpected token: " + h.getToken());
		}

		return fun;
	}

	fTypeDef typeDef() {
		h.accept(fTokenKind.T_TYPE);
		fTypeDef t = new fTypeDef((NamedToken) h.next());
		if(h.isTkLBracket()){
			t.setTypeParams(variantTypeParams());
		}
		h.acceptOpChar(OpChar.ASSIGN);
		t.setAssignedType(type());
		return t;
	}

	fParamClauses  paramClauses(){
		fParamClauses pcs = new fParamClauses();
		while(h.isTkLParen()) {
			if(h.isLa(1, fTokenKind.T_IMPLICIT)){
				pcs.setImplicitParams(paramClause(true));
				break;
			}
			pcs.addParams(paramClause(false));
		}
		return pcs;
	}

	List<fParam> paramClause(boolean isImplicit) {
		List<fParam> params = new ArrayList<>();
		h.accept(fTokenKind.T_LPAREN);
		if(!h.isTkRParen()){
			if(isImplicit){
				h.accept(fTokenKind.T_IMPLICIT);
			}
			while(true){
				params.add(param());
				if(h.isTkComma()){
					h.next(); continue;
				}
				break;
			}
		}
		h.accept(fTokenKind.T_RPAREN);
		return params;
	}

	fParam param(){
		fParam p = new fParam((NamedToken) h.next());
		if(h.isColonOpT(0)){
			h.next();
			p.setParamType(paramType(false));
		}
		if(h.isAssignOpT(0)){
			h.next();
			p.setDefaultValue(expr(null));
		}
		return p;
	}

	List<fTypeParam> funTypeParams(){
		List<fTypeParam> params = new ArrayList<>();
		h.accept(fTokenKind.T_LBRACKET);
		while(true){
			params.add(typeParam());
			if(h.isTkComma()){
				h.next(); continue;
			}
			break;
		}
		h.accept(T_RBRACKET);
		return params;
	}



	List<fVariantTypeParam> variantTypeParams() {
		List<fVariantTypeParam> params = new ArrayList<>();
		h.accept(fTokenKind.T_LBRACKET);
		while(true){
			params.add(variantTypeParam());
			if(h.isTkComma()){
				h.next(); continue;
			}
			break;
		}
		h.accept(T_RBRACKET);
		return params;
	}

	fTypeParam typeParam() {
		fTypeParam p = new fTypeParam((NamedToken) h.next());
		if(h.isTkLBracket()){
			p.setVariantTypeParams(variantTypeParams());
		}
		if(h.isTkLowerBound()){
			p.setLowerBound(type());
		}
		if(h.isTkUpperBound()){
			p.setUpperBound(type());
		}
		if(h.isColonOpT(0)){
			p.setType(type());
		}

		return p;
	}

	fVariantTypeParam variantTypeParam() {
		fVariantTypeParam p = new fVariantTypeParam((NamedToken) h.next());
		if(h.isPlusOpT(0)){
			h.next();
			p.setVariance(fVariantTypeParam.fVariance.VARIANT);
		} else if(h.isMinusOpT(0)){
			h.next();
			p.setVariance(fVariantTypeParam.fVariance.INVARIANT);
		}
		if(h.isTkLowerBound()){
			p.setLowerBound(type());
		}
		if(h.isTkUpperBound()){
			p.setUpperBound(type());
		}
		if(h.isColonOpT(0)){
			p.setType(type());
		}

		return p;
	}

	AstProdSubTreeN varDef() {
		// patDef() + "ids: Type = _"
		return patDef();
	}


	AstProdSubTreeN patterns() {
		Ast a = new Ast();
		while(true){
			a.setRight(pattern());
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PATTERNS, a);
	}

	AstProdSubTreeN pattern() {
		Ast a = new Ast();
		while (true){
			a.setRight(pattern1());
			if(h.isPipeOpT(0)){
				h.insertOperator(a, fLangOperatorKind.O_PIPE, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PATTERN, a);
	}

	AstProdSubTreeN pattern1() {
		Ast a = new Ast();
		a.setRight(stableId(false));
		h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
		a.setRight(type());
		return new  AstProdSubTreeN(GrmPrd.PATTERN_1, a);
	}

	AstProdSubTreeN patDef(){
		Ast a = new Ast();
		while(true){
			a.setRight(pattern2());
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		if(h.isColonOpT(0)){
			h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
			a.setRight(type());
		}
		h.insertOperator(a, fLangOperatorKind.O_ASSIGN,  h.acceptOpChar(OpChar.ASSIGN));
		a.setRight(expr(null));
		return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
	}

	AstProdSubTreeN pattern2() {
		Ast a = new Ast();
		switch (h.TKnd()){
			case T_ID: {
				if(h.isAtOpT(1)){
					a.setRight(new fStableId(false));
					h.insertOperator(a, fLangOperatorKind.O_AT,  h.next());
					a.setRight(pattern3());
					return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
				}
				//fall through
			}
			case T_THIS: case T_SUPER: case T_LPAREN: {
				a.setRight(pattern3());
				return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
			}
			default:
				throw new RuntimeException("Pattern in unexpected place: " + h.getToken());
		}
	}

	AstProdSubTreeN pattern3(){
		Ast a = new Ast();
		loop:
		while(true){
			switch (h.TKnd()){
				case T_ID: case T_THIS: case T_SUPER: {
					pattern3Id(a);
				}
				case T_LPAREN:{
					h.accept(fTokenKind.T_LPAREN);
					a.setRight(patterns());
					h.accept(fTokenKind.T_RPAREN);
					return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
				}
				default:
					break loop;
			}
		}
		return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
	}

	void pattern3Id(Ast a){
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(stableId(false));
				break;
			}
			case AST_OPERAND:{
				//should be plain T_ID
				h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()),  h.next());
				break;
			}
			default:
				throw new RuntimeException("Pattern3Id in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLCURL(Ast a){
		//BlockExpr
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				h.accept(fTokenKind.T_LCURL);
				if(h.TKnd() == fTokenKind.T_CASE){
					caseClauses(a);
				} else {
					block();
				}
				h.accept(fTokenKind.T_RCURL);
				break;
			}
			default:
				throw new RuntimeException("LCURL in unexpected place: " + a.astLastNKnd());
		}
	}

	void caseClauses(Ast a) {}

	AstProdSubTreeN typeArgs() {
		return null;
	}

	AstProdSubTreeN exprs() {
		Ast aPrn = new Ast();
		Ast aChd = null;
		int lparSz = h.skipLPar(); assert lparSz > 0;
		while(true){
			aPrn.setRight(expr(aChd));
			while(h.isTkComma()){
				h.insertOperator(aPrn, fLangOperatorKind.O_COMMA, h.next());
				aPrn.setRight(expr(null));
			}
			int n = h.skipRPar(lparSz); assert n > 0;
			lparSz -= n; assert lparSz >= 0;
			if(lparSz == 0){
				return new AstProdSubTreeN(GrmPrd.EXPRS_OR_BINDINGS, aPrn);
			}
			aChd = new Ast();
			aChd.setRight(new AstProdSubTreeN(GrmPrd.SUBTREE, aPrn));
			aPrn = new Ast();
			aPrn.setRight(new AstProdSubTreeN(GrmPrd.SUBTREE, aChd));
		}
	}


	fImport importClause() {
		h.accept(fTokenKind.T_IMPORT);
		fImport im = new fImport();
		while (true){
			fImport.fImportExpr imEx = new fImport.fImportExpr(stableId(false));
			if(h.isTkLCurl()){
				imEx.setSelectors(importSelectors());
			}
			im.addImportExpr(imEx);
			if(h.isTkComma()){
				h.next();
			} else {
				break;
			}
		}
		return im;
	}

	List<fImport.fImportSelector> importSelectors() {
		h.accept(T_LCURL);
		List<fImport.fImportSelector> selectors = new ArrayList<>();
		while (true) {
			NamedToken from = (NamedToken) h.accept(T_ID);
			NamedToken to = null;
			if(h.isTkFatArrow()){
				h.next();
				to = (NamedToken) h.accept(T_ID);
			}
			selectors.add(new fImport.fImportSelector(from, to));
			if(h.isTkComma()){
				h.next();
				continue;
			}
			break;
		}
		h.accept(T_RCURL);
		return selectors;
	}

	AstProdSubTreeN blockOrTemplateStat(GrmPrd prd) {
		final Ast a = new Ast();
		switch (h.TKnd()){
			case T_IMPORT: {
				a.setRight(importClause());
				break;
			}
			case T_CASE: case T_CLASS:  case T_OBJECT: {
				a.setRight(classObjectDef());
				break;
			}
			case T_TRAIT: {
				a.setRight(traitDef());
				break;
			}
			case T_LAZY: case T_IMPLICIT: case T_ABSTRACT: case T_FINAL: case T_SEALED:{
				//modifiers();
				break;
			}
			case T_VAL: {
				a.setRight(patDef());
				break;
			}
			case T_VAR: {
				a.setRight(varDef());
				break;
			}
			case T_DEF: {
				a.setRight(funDef());
				break;
			}
			case T_TYPE: {
				a.setRight(typeDef());
				break;
			}
			case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN:
			case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: case T_LCURL: case T_NEW:
			{
				a.setRight(expr(null));
			}
			default:
				break;
		}
		return new AstProdSubTreeN(prd, a);
	}

	fIds packageClause() {
		h.accept(T_PACKAGE);
		return new fIds(ids());
	}

	fPackages packages() {
		List<fIds> fs = new ArrayList<>();
		while (h.isTkPackage()){
			fs.add(packageClause());
		}
		return new fPackages(fs);
	}

	public fCompilationUnit compilationUnit() {
		final Ast a = new Ast();
		if(h.isTkPackage()){
			a.setRight(packages());
			h.insertSemicolonOperator(a);
		}

		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_IMPORT: {
					a.setRight(importClause());
					continue;
				}
				case T_CASE: case T_CLASS: case T_OBJECT: {
					a.setRight(classObjectDef());
					continue;
				}
				case T_TRAIT: {
					a.setRight(traitDef());
					continue;
				}
				default:
					break loop;
			}
		}
		return new fCompilationUnit(new AstProdSubTreeN(GrmPrd.COMPILATION_UNIT, a));
	}
}
