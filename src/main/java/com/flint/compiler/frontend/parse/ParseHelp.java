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

	fToken getPrevToken() {
		return prevToken;
	}

	boolean isTkComma() {
		return token.kind == T_COMMA;
	}

	boolean isTkWith() {
		return token.kind == T_WITH;
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

	void acceptOpChar(OpChar opChar) {
		if (token.opChar() == opChar) {
			next();
		} else {
			throw new AssertionError("Expected " + opChar + " but found " + token.opChar());
		}
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

	void skipSemi() {
		while (token.kind == T_SEMI || token.kind == T_NL) {
			next();
		}
	}

	void skipNL() {
		while (token.kind == T_NL) {
			next();
		}
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

	void insertOperator(Ast a, fLangOperatorKind k, NamedToken operatorToken) {
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
	}
}
