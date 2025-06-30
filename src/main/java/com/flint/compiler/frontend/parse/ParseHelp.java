package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.OpChar;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorMap;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;
import com.flint.compiler.frontend.parse.utils.Ast;

import static com.flint.compiler.frontend.parse.lex.token.fTokenKind.*;

public class ParseHelp {

	private fToken prevToken;
	private fToken token;
	private fLexer lexer;

	ParseHelp(fLexer lexer) {
		this.lexer = lexer;
		this.token = lexer.nextToken();
	}

	fToken getToken() {
		return token;
	}

	NamedToken getAsNamedToken() {
		if (token instanceof NamedToken namedToken) {
			return namedToken;
		} else {
			throw new AssertionError("Expected NamedToken but found " + token.kind);
		}
	}

	boolean isTkLowerBound() {
		return token.kind == T_LOWER_BOUND;
	}

	boolean isTkUpperBound() {
		return token.kind == T_UPPER_BOUND;
	}

	boolean isTkLParen() {
		return token.kind == T_LPAREN;
	}

	boolean isTkRParen() {
		return token.kind == T_RPAREN;
	}

	boolean isTkImplicit() {
		return token.kind == T_IMPLICIT;
	}

	boolean isTkLBracket() {
		return token.kind == T_LBRACKET;
	}

	boolean isTkExtends() {
		return token.kind == T_EXTENDS;
	}

	boolean isTkLCurl() {
		return token.kind == T_LCURL;
	}

	boolean isTkCase() {
		return token.kind == T_CASE;
	}

	boolean isTkIF() {
		return token.kind == T_IF;
	}

	boolean isTkComma() {
		return token.kind == T_COMMA;
	}

	boolean isTkWith() {
		return token.kind == T_WITH;
	}

	boolean isTkFatArrow() {
		return token.kind == T_FAT_ARROW;
	}

	boolean isTkPackage() {
		return token.kind == T_PACKAGE;
	}

	boolean isTkTID() {
		return token.kind == T_ID;
	}

	boolean isTkTHIS() {
		return token.kind == T_THIS;
	}

	boolean isTkOpCharAssign() {
		return token.kind == T_ID && token.opChar() == OpChar.ASSIGN;
	}

	boolean isTkSemicolon(){
		return token.kind == T_SEMICOLON;
	}

	boolean isTkNL() {
		return token.kind == T_NL;
	}

	fTokenKind TKnd() {
		return token.kind;
	}

	fToken next() {
		prevToken = token;
		token = lexer.nextToken();
		System.out.println("Token: " + token);
		return prevToken;
	}

	fToken acceptOpChar(OpChar opChar) {
		assert token.kind == T_ID && token.opChar() == opChar;
		return next();
	}

	fToken accept(fTokenKind kind) {
		if (token.kind != kind) {
			throw new AssertionError("Expected " + kind + " but found " + token.kind);
		}
		return next();
	}

	fToken lookAhead(int n) {
		return lexer.lookAhead(n);
	}

	boolean isLa(int la, fTokenKind... types) {
		fToken laToken = lookAhead(la);
		for (fTokenKind type : types) {
			if (laToken.kind == type) {
				return true;
			}
		}
		return false;
	}

	boolean isLaIdOpChar(int la, OpChar... ops) {
		return isLaOpChar(la, T_ID, ops);
	}

	boolean isLaOpChar(int la, fTokenKind tk, OpChar... ops) {
		fToken laTok = lookAhead(la);
		if(laTok.kind == tk && laTok.opChar() != OpChar.INVALID) {
			for (OpChar op : ops) {
				if (laTok.opChar() == op) {
					return true;
				}
			}
		}
		return false;
	}

	boolean isAtOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.AT);
	}

	boolean isPlusOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.PLUS);
	}

	boolean isMinusOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.MINUS);
	}

	boolean isColonOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.COLON);
	}

	boolean isAssignOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.ASSIGN);
	}

	boolean isPoundOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.POUND);
	}

	boolean isPipeOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.PIPE);
	}

	boolean isStarOpT(int la) {
		return isLaOpChar(la, T_ID, OpChar.STAR);
	}

	fLangOperatorKind getOperatorKind(fToken token) {
		return fLangOperatorMap.getOperatorKind(token);
	}

	int skipLPar() {
		int count = 0;
		while (token.kind == fTokenKind.T_LPAREN) {
			count++;
			next();
		}
		return count;
	}

	int skipRPar(int lparSz) {
		assert lparSz > 0;
		while (lparSz > 0 && token.kind == fTokenKind.T_RPAREN) {
			lparSz--;
			next();
		}
		return lparSz;
	}

	void skipNL() {
		while (isTkNL()) {
			next();
		}
	}

	void skipSemi() {
		if(isTkSemicolon()){
			next();
			return;
		}
		skipNL();
	}

	void expectOneOf(int la, fTokenKind... types) {
		assert types.length > 0;
		fToken _laToken = lookAhead(la);
		for (fTokenKind type : types) {
			if (_laToken.kind == type) {
				return;
			}
		}
		throw new RuntimeException("Unexpected token: " + token.kind);
	}

	void insertSemicolonOperator(Ast a){
		insertOperator(a, fLangOperatorKind.O_SEMICOLON, fToken.SEMICOLON);
	}

	void insertOperator(Ast a, fLangOperatorKind k, fToken operatorToken) {
		AstNod last = a.astLastN();
		assert !last.isOperator(): "Last node should not be operator";
		AstOperatorNod prn = last.getAstParentN();
		assert prn.isOperator() && prn.getAstRightN() == last;
		AstOperatorNod  op = new AstOperatorNod(k, operatorToken);
		if(prn == a.rootOp){
			a.rootOp.setAstRightN(op);
			op.setAstLeftN(last);
		} else {
			if(k.precedence() > prn.getLangOperatorKind().precedence() || k.isRightAssociative){
				AstNod right = prn.getAstRightN(); assert !right.isOperator();
				prn.setAstRightN(op);
				op.setAstLeftN(right);
			} else {
				AstOperatorNod grandParent = prn.getAstParentN(); assert grandParent.isOperator();
				grandParent.setAstRightN(op);
				op.setAstLeftN(prn);
			}
		}
		a.setAstLastN(op);
	}
}
