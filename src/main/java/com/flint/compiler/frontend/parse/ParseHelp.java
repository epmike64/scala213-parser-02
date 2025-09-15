package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstOperatorNod;
import com.flint.compiler.frontend.parse.lex.fLexer;

import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorMap;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;
import com.flint.compiler.frontend.parse.utils.Ast;

import java.util.Stack;

import static com.flint.compiler.frontend.parse.lex.token.fTokenKind.*;

public class ParseHelp {

	private fToken prevToken;
	private fToken token = fToken.SOF;
	private fLexer lexer;
	private final Stack<Boolean> isNLEnabledStack = new Stack<>();
	private int nlCount;
	private int tc = 0;

	ParseHelp(fLexer lexer) {
		this.lexer = lexer;
		isNLEnabledStack.push(true); // Default is NL enabled
		next();
	}

	int pushNLEnabled(boolean enabled) {
		isNLEnabledStack.push(enabled);
		return isNLEnabledStack.size();
	}

	boolean popNLEnabled(int sz, boolean expectedTop) {
		assert isNLEnabledStack.size() == sz && isNLEnabledStack.peek() == expectedTop;
		boolean v = isNLEnabledStack.pop();
		if (isNLEnabledStack.isEmpty()) throw new RuntimeException("NLStack is empty");
		return v;
	}

	boolean isNLEnabled() {
		return isNLEnabledStack.peek();
	}

	private fToken firstNonNL(int from) {
		for (int i = from; ; i++) {
			fToken laToken = lookAhead(i);
			if (laToken.kind != T_NL) {
				return laToken;
			}
		}
	}

	int getNLCount() {
		return nlCount;
	}

	fToken next() {
		prevToken = token;
		loop:
		while (true) {
			nlCount = 0;
			token = lexer.nextToken();
			if (token.kind != T_NL) {
				break;
			}
			nlCount++;
			while (isLa(1, T_NL)) {
				lexer.nextToken();
				nlCount++;
			}
			//1. Test previous token to see if it can terminate a statement
			switch (prevToken.kind) {
				case T_ID: case T_THIS: case T_INT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_FLOAT_LIT: case T_NULL:
				case T_TRUE: case T_FALSE: case T_RETURN: case T_TYPE: case T_UNDERSCORE:
				case T_RPAREN: case T_RBRACKET: case T_RCURL: {
					break;// Previous can terminate a statement, but what about the next token ?
				}
				default:
					//Previous cannot terminate a statement, so continue
					continue loop; // NL is discarded
			}
			//2. Test LookAhead token to see if it can start a statement
			fToken la = lexer.lookAhead(1);
			switch (la.kind) {
				//can NOT start a statement
				case T_CATCH: case T_ELSE: case T_EXTENDS: case T_FINALLY:
				case T_MATCH: case T_WITH: case T_YIELD: case T_COMMA:
				case T_DOT: case T_COLON: case T_ASSIGN: case T_FAT_ARROW:
				case T_IN: case T_UPPER_BOUND: case T_LOWER_BOUND:
				case T_CONTEXT_BOUND: case T_POUND: case T_LBRACKET:
				case T_RPAREN: case T_RBRACKET: case T_RCURL: {
					continue loop; // NL is discarded
				}
				case T_CASE: {
					fToken f = firstNonNL(2);
					if (f.kind != T_CLASS && f.kind != T_OBJECT) {
						continue loop; // NL is discarded
					}
					break;
				}
				default:
					break;
			}
			if (isNLEnabled()) {
				break; // NL is returned
			}
		}
		System.out.println((tc++) + " : " + token);
		return prevToken;
	}

	fTokenKind getTokenKind() {
		return token.kind;
	}

	boolean isTkColon() {
		return token.kind == T_COLON;
	}

	boolean isTkAssign() {
		return token.kind == T_ASSIGN;
	}

	boolean isTkPlus() {
		return token.kind == T_PLUS;
	}

	boolean isTkMinus() {
		return token.kind == T_MINUS;
	}

	boolean isTkPipe() {
		return token.kind == T_PIPE;
	}

	boolean isTkYield() {
		return token.kind == T_YIELD;
	}

	boolean isTkRCurl() {
		return token.kind == T_RCURL;
	}

	boolean isTkIf() {
		return token.kind == T_IF;
	}

	boolean isTkCatch() {
		return token.kind == T_CATCH;
	}

	boolean isTkFinally() {
		return token.kind == T_FINALLY;
	}

	boolean isTkStar() {
		return token.kind == T_STAR;
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

	boolean isTkContextBound() {
		return token.kind == T_CONTEXT_BOUND;
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

	boolean isTkElse() {
		return token.kind == T_ELSE;
	}

	boolean isTkDot() {
		return token.kind == T_DOT;
	}

	boolean isTkId() {
		return token.kind == T_ID;
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

	boolean isTkSemicolon() {
		return token.kind == T_SEMICOLON;
	}

	boolean isTkNL() {
		return token.kind == T_NL;
	}

	boolean isTkUnderscore() {
		return token.kind == T_UNDERSCORE;
	}

	boolean isTkImport() {
		return token.kind == T_IMPORT;
	}

	fTokenKind tKnd() {
		return token.kind;
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


	fLangOperatorKind getOperatorKind(fToken token) {
		return fLangOperatorMap.getOperatorKind(token);
	}


	void skipNL() {
		if (isTkNL()) next();
	}

	void skipAllNLs() {
		while (isTkNL()) next();
	}

	void skipSemi() {
		switch (token.kind) {
			case T_SEMICOLON: {
				next();
				break;
			}
			case T_NL: {
				while (isTkNL()) next();
				break;
			}
			default:
				break;
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

	void insertStmtSepOper(Ast a) {
		insertOperator(a, fLangOperatorKind.O_STMT_SEP, fToken.STMT_SEP);
	}

	void insertOperator(Ast a, fLangOperatorKind k, fToken operatorToken) {
		AstNod last = a.astLastN();
		assert !last.isOperator() : "Last node should not be operator";
		AstOperatorNod prn = last.getAstParentN();
		assert prn.isOperator() && prn.getAstRightN() == last;
		AstOperatorNod op = new AstOperatorNod(k, operatorToken);
		if (prn == a.rootOp) {
			a.rootOp.setAstRightN(op);
			op.setAstLeftN(last);
		} else {
			if (k.precedence() > prn.getLangOperatorKind().precedence() || k.isRightAssociative) {
				AstNod right = prn.getAstRightN();
				assert !right.isOperator();
				prn.setAstRightN(op);
				op.setAstLeftN(right);
			} else {
				AstOperatorNod grandParent = prn.getAstParentN();
				assert grandParent.isOperator();
				grandParent.setAstRightN(op);
				op.setAstLeftN(prn);
			}
		}
		a.setAstLastN(op);
	}
}
