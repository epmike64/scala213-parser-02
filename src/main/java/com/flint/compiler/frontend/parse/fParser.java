package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fFunction;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fParamType;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fParameterizedType;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fType;
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

public class fParser {

	private final ParseHelp h;

	public fParser(fLexer lexer) {
		h = new ParseHelp(lexer);
	}

	fParamType paramType() {
		Ast a = new Ast();
		boolean isFatArrow = false;
		boolean isStar = false;
		if(h.isLa(0, fTokenKind.T_FAT_ARROW)){
			isFatArrow = true; h.next();
		}
		fType t = type();
		if(h.isStarOpT(0)){
			isStar = true; h.next();
		}
		return new fParamType(t, isFatArrow, isStar);
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
					typeLParen(a);
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
					h.insertOperator(a, fLangOperatorKind.O_WITH, (NamedToken) h.next());
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
				h.insertOperator(a, fLangOperatorKind.O_COMMA, (NamedToken)h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.TYPES, a);
	}

	AstProdSubTreeN paramTypes() {
		Ast a = new Ast();
		while(true){
			a.setRight(paramType());
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, (NamedToken)h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PARAM_TYPES, a);
	}

	void typeFatArrow(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, (NamedToken) h.next());
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
				h.insertOperator(a, fLangOperatorKind.O_BRACKETS, (NamedToken) h.next());
				a.setRight(types());
				h.accept(fTokenKind.T_RBRACKET);
			}
			default:
				throw new RuntimeException("LBracket in unexpected place: " + a.astLastNKnd());
		}
	}

	void typeLParen(Ast a){
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				h.accept(fTokenKind.T_LPAREN);
				a.setRight(paramTypes());
				h.accept(fTokenKind.T_RPAREN);
				break;
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
						h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, (NamedToken)h.next());
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

	fPath path(Ast a){
		return (fPath) stableId(a, true);
	}

	StableId stableId(Ast a, boolean isPath){
		return null;
	}

	void typeTID(Ast a){
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(stableId(a, false));
				break;
			}
			case AST_OPERAND:{
				/* ID OPERATOR */
				if(h.isPoundOpT(0)){
					h.insertOperator(a, fLangOperatorKind.O_POUND, (NamedToken) h.next());
					a.setRight(new StableId((NamedToken) h.accept(fTokenKind.T_ID)));
				} else {
					h.insertOperator(a, fLangOperatorKind.O_ID_SMBLC_RIGHT_ASSC, (NamedToken) h.next());
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
				a.setRight(path(a));
				break;
			}
			case AST_OPERAND:{
				/* ID OPERATOR */
				if(h.isColonOpT(0)){
					h.insertOperator(a, fLangOperatorKind.O_COLON, (NamedToken)h.next());
					a.setRight(type());
					a.setContinue(false);
				}
				else if(h.isAssignOpT(0)) {
					h.insertOperator(a, fLangOperatorKind.O_ASSIGN, (NamedToken) h.next());
					a.setRight(expr(a));
					a.setContinue(false);
				} else {
					h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), (NamedToken) h.next());
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

	AstProdSubTreeN exprNEW(){
		h.accept(fTokenKind.T_NEW);
		switch (h.TKnd()){
			case T_LCURL: {
				return templateBody();
			}
			case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: {
				return classTemplate();
			}
			default:
				throw new RuntimeException("NEW in unexpected place: " + h.getToken());
		}
	}


	AstProdSubTreeN classTemplate(){
		Ast a = new Ast();
		return new AstProdSubTreeN(GrmPrd.CLASS_TEMPLATE, a);
	}

	AstProdSubTreeN templateBody(){
		h.accept(fTokenKind.T_LCURL);
		Ast a = new Ast();
		loop:
		while(true){
			switch (h.TKnd()){
				case T_IMPORT: {
					//importClause();
					continue;
				}
				case T_LAZY: case T_IMPLICIT: case T_ABSTRACT: case T_FINAL: case T_SEALED:{
					//modifiers();
					continue;
				}
				case T_VAL: {
					a.setRight(patDef());
					continue;
				}
				case T_VAR: {
					a.setRight(varDef());
					continue;
				}
				case T_DEF: {
					a.setRight(funDef());
					continue;
				}
				case T_TYPE: {
					//typeDef();
					continue;
				}
				case T_CASE: case T_CLASS:  case T_OBJECT: {
					//classDef();
					continue;
				}
				case T_TRAIT: {
					//traitDef();
					continue;
				}
				default:
					break loop;
			}
		}
		h.accept(fTokenKind.T_RCURL);
		return new AstProdSubTreeN(GrmPrd.TEMPLATE_BODY, a);
	}

	fFunctionDef  funDef(){
		h.accept(fTokenKind.T_DEF);
		Ast a = new Ast();
		fFunctionDef funDef = new fFunctionDef((NamedToken) h.next());
		if(h.isTkLBracket()){

			funDef.setTypeParams(funTypeParamClause());

		}
		return funDef;
	}

	List<fTypeParam> funTypeParamClause(){
		List<fTypeParam> params = new ArrayList<>();
		h.accept(fTokenKind.T_LBRACKET);
		while(true){
			params.add(typeParam());
			if(h.isTkComma()){
				h.next(); continue;
			}
			break;
		}
		h.accept(fTokenKind.T_RBRACKET);
		return params;
	}

	fTypeParam typeParam() {
		fTypeParam p = new fTypeParam((NamedToken) h.next());
		if(h.isTkLBracket()){

		}
	}

	fVariantTypeParam varientTypeParam() {
		fVariantTypeParam p = new fVariantTypeParam((NamedToken) h.next());
		if(h.isTkLBracket()){
			h.accept(fTokenKind.T_LBRACKET);
			p.setTypeArgs(typeArgs());
			h.accept(fTokenKind.T_RBRACKET);
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
				h.insertOperator(a, fLangOperatorKind.O_COMMA, (NamedToken)h.next());
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
				h.insertOperator(a, fLangOperatorKind.O_PIPE, (NamedToken)h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PATTERN, a);
	}

	AstProdSubTreeN pattern1() {
		Ast a = new Ast();
		a.setRight(stableId(a, false));
		h.insertOperator(a, fLangOperatorKind.O_COLON, (NamedToken)h.next());
		a.setRight(type());
		return new  AstProdSubTreeN(GrmPrd.PATTERN_1, a);
	}

	AstProdSubTreeN patDef(){
		Ast a = new Ast();
		while(true){
			a.setRight(pattern2());
			if(h.isTkComma()){
				h.insertOperator(a, fLangOperatorKind.O_COMMA, (NamedToken)h.next());
				continue;
			}
			break;
		}
		if(h.isColonOpT(0)){
			h.insertOperator(a, fLangOperatorKind.O_COLON, (NamedToken)h.next());
			a.setRight(type());
		}
		h.insertOperator(a, fLangOperatorKind.O_ASSIGN, (NamedToken) h.acceptOpChar(OpChar.ASSIGN));
		a.setRight(expr(null));
		return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
	}

	AstProdSubTreeN pattern2() {
		Ast a = new Ast();
		switch (h.TKnd()){
			case T_ID: {
				if(h.isAtOpT(1)){
					a.setRight(new StableId((NamedToken) h.next()));
					h.insertOperator(a, fLangOperatorKind.O_AT, (NamedToken) h.next());
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
				a.setRight(stableId(a, false));
				break;
			}
			case AST_OPERAND:{
				//should be plain T_ID
				h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), (NamedToken) h.next());
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

	void block() {
		switch (h.TKnd()){
			case T_IMPORT:{
				h.next();
				//importClauses();
				return;
			}
		}
		switch (h.TKnd()){
			case T_LAZY: case T_IMPLICIT: case T_ABSTRACT: case T_FINAL: case T_SEALED:{
				break;
			}
		}
		switch (h.TKnd()){

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
				h.insertOperator(aPrn, fLangOperatorKind.O_COMMA, (NamedToken)h.next());
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
}
