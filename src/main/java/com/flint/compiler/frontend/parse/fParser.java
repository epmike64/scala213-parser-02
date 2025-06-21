package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fFunction;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fParamType;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fParameterizedType;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fType;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.StableId;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.fPath;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.OpChar;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.utils.Ast;

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
				case T_LPAREN: //(ParamType, ParamType) | (Type, Type)
					typeLParen(a);
					continue;
				case T_LBRACKET:
					continue;
				case T_ID: case T_SUPER: case T_THIS:
					typeTID(a);
					continue;
				case T_WITH:
					continue;
				default:
					break loop;
			}
		}
		return new fType(new AstProdSubTreeN(GrmPrd.TYPE, a));
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

	void typeLParen(Ast a){
		h.accept(fTokenKind.T_LPAREN);
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(paramTypes());
				break;
			}
			default:
				throw new RuntimeException("LParen in unexpected place: " + a.astLastNKnd());
		}
	}

	void typeTID(Ast a){
		switch (a.astLastNKnd()){
			case AST_ROOT_OPERATOR: case AST_OPERATOR:{
				a.setRight(stableId(a, false));
				break;
			}
			default:
				throw new RuntimeException("TID in unexpected place: " + a.astLastNKnd());
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
					exprNEW(a);
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
					h.insertOperator(a, fLangOperatorKind.O_ID_SMBLC_LEFT_ASSC, (NamedToken) h.next());
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
	void exprNEW(Ast a){}
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
		Ast aChd = new Ast();
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
				return new AstProdSubTreeN(GrmPrd.EXPRS_BINDINGS, aPrn);
			}
			aChd = new Ast();
			aChd.setRight(new AstProdSubTreeN(GrmPrd.SUBTREE, aPrn));
			aPrn = new Ast();
			aPrn.setRight(new AstProdSubTreeN(GrmPrd.SUBTREE, aChd));
		}
	}
}
