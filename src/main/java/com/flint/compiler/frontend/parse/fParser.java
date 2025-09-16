package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.leaves.node.*;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.*;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.fLangOperatorKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;
import com.flint.compiler.frontend.parse.utils.Ast;

import java.util.ArrayList;
import java.util.List;

import static com.flint.compiler.frontend.parse.lex.token.fTokenKind.*;


public class fParser {

	private final ParseHelp h;

	public fParser(fLexer lexer) {
		h = new ParseHelp(lexer);
	}

	List<fNamedToken> ids(boolean isQualified) {

		List<fNamedToken> ids = new ArrayList<>();
		while (true) {
			assert h.tKnd() == T_ID;
			ids.add((fNamedToken) h.next());
			if (isQualified && h.isTkDot()) {
				h.next();
			} else if (h.isTkComma()) {
				h.next();
			} else {
				break;
			}
		}
		return ids;
	}

	fTypeArgs typeArgs() {
		h.accept(T_LBRACKET);
		AstProdSubTreeN types = types();
		h.accept(T_RBRACKET);
		return new fTypeArgs(types);
	}

	fParamType paramType(boolean isSimpleType) {
		boolean isFatArrow = false;
		boolean isStar = false;
		if (!isSimpleType) {
			if (h.isLa(0, fTokenKind.T_FAT_ARROW)) {
				isFatArrow = true;
				h.next();
			}
		}
		fType t = type();
		if (!isSimpleType) {
			if (h.getTokenKind() == fTokenKind.T_STAR) {
				isStar = true;
				h.next();
			}
		}
		return new fParamType(t, isFatArrow, isStar);
	}

	fParamType simpleType() {
		Ast a = new Ast();
		simpleType_2(a);
		return new fParamType(new fType(new AstProdSubTreeN(GrmPrd.SIMPLE_TYPE, a)), false, false);
	}

	void simpleType_2(Ast a) {
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_ID: {
					switch (a.astLastNKnd()) {
						case AST_OPERAND: {
							break loop;
						}
						default:
							break;
					}
				}
				// fall through
				case T_SUPER: case T_THIS: {
					a.setRight(path());
					//if dot -> type
					continue;
				}
				case T_LPAREN: {
					typeLParen(a, true);
					if (a.isContinue()) continue;
					break loop;
				}
				case T_LBRACKET: {
					typeLBracket(a);
					continue;
				}
				default:
					break loop;
			}
		}
	}


	fType type() {
		Ast a = new Ast();
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_ID: {
					switch (a.astLastNKnd()) {
						case AST_OPERAND: {
							h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), h.next());
							continue;
						}
						default:
							break;
					}
					//fall through
				}
				case T_SUPER: case T_THIS: case T_LPAREN: {
					simpleType_2(a);
					continue;
				}

				case T_FAT_ARROW: {
					if (h.isLa(1, T_ID, T_SUPER, T_THIS, T_LPAREN)) {
						typeFatArrow(a);
					}
					break loop;
				}
				case T_WITH: {
					typeWith(a);
					continue;
				}
				default:
					break loop;
			}
		}
		return new fType(new AstProdSubTreeN(GrmPrd.TYPE, a));
	}

	void typeWith(Ast a) {
		while (h.isTkWith()) {
			switch (a.astLastNKnd()) {
				case AST_OPERAND: {
					h.insertOperator(a, fLangOperatorKind.O_WITH, h.next());
					a.setRight(type());
					continue;
				}
				default:
					throw new RuntimeException("With in unexpected place: " + a.astLastNKnd());
			}
		}
	}

	AstProdSubTreeN types() {
		Ast a = new Ast();
		while (true) {
			a.setRight(type());
			if (h.isTkComma()) {
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.TYPES, a);
	}

	List<fParamType> paramTypes(boolean isSimpleType) {
		List<fParamType> ps = new ArrayList<>();
//		Ast a = new Ast();
		while (true) {
			ps.add(paramType(isSimpleType));
			if (h.isTkComma()) {
				h.next();
				//h.insertOper(a, fLangOperKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return ps;
	}

	void typeFatArrow(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, h.next());
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
				int sz = h.pushNLEnabled(false);
				h.insertOperator(a, fLangOperatorKind.O_BRACKETS, h.accept(fTokenKind.T_LBRACKET));
				a.setRight(types());
				h.popNLEnabled(sz, false);
				h.accept(T_RBRACKET);
				break;
			}
			default:
				throw new RuntimeException("LBracket in unexpected place: " + a.astLastNKnd());
		}
	}

	void typeLParen(Ast a, boolean isSimpleType) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(fTokenKind.T_LPAREN);
				List<fParamType> ps = paramTypes(isSimpleType);
				a.setRight(new fParamTypeList(ps));
				h.accept(fTokenKind.T_RPAREN);
				break;
			}
			case AST_OPERAND: {
				a.setContinue(false);
				break;
			}
			default:
				throw new RuntimeException("LParen in unexpected place: " + a.astLastNKnd());
		}
	}

	AstProdSubTreeN postfixExpr() {
		Ast a = new Ast();
		boolean isPlusMinus;
		loop:
		while (true) {
			isPlusMinus = true;
			switch (h.tKnd()) {
				case T_NEW: {
					a.setRight(exprNEW());
					continue;
				}
				case T_NL: {
					if(!h.isLa(1, T_LCURL)) {
						break loop;
					}
					h.next();
					// fall through
				}
				case T_LCURL: {
					exprLCURL(a);
					continue;
				}
				case T_DOT: {
					exprDot(a);
					continue;
				}
				case T_PLUS: case T_MINUS: {
					isPlusMinus = true;
					//fall through
				}
				case T_TILDE: case T_EXCLAMATION: { //PrefixOperator  :: '-' | '+' | '~' | '!'
					if(a.astLastNKnd() == AstNodKind.AST_ROOT_OPERATOR || a.astLastNKnd() == AstNodKind.AST_OPERATOR){
						switch(h.lookAhead(1).kind){
							case T_NEW: case T_LCURL: case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: case T_UNDERSCORE:
							case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE: case T_NULL:{
								h.next();
								continue; //prefix operator
							}
							default:
								throw new RuntimeException("Unexpected token after prefix operator: " + h.getToken());
						}
					} else if(!isPlusMinus){
						throw new RuntimeException("Unexpected token after prefix operator: " + h.getToken());
					}
					// fall through
				}
				case T_STAR: case T_FORWARD_SLASH: case T_PERCENT: {
					exprMathOper(a);
					continue;
				}
				case T_LT: case T_LTE: case T_GT: case T_GTE: case T_EQUAL: case T_NOT_EQUAL: {
					exprRelationalOper(a);
					continue;
				}
				case T_LOGICAL_AND:  case T_LOGICAL_OR: {
					exprLogicalOper(a);
					continue;
				}
				case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT:
				case T_NULL: case T_TRUE: case T_FALSE: {
					exprLit(a);
					continue;
				}
				case T_ID: case T_THIS: case T_SUPER: {
					exprTID(a, true);
					continue;
				}
				case T_LPAREN: {
					exprLParen(a);
					continue;
				}
				case T_LBRACKET: {
					exprLBracket(a);
					continue;
				}
				default:
					break loop;
			}
		}
		return new AstProdSubTreeN(GrmPrd.POSTFIX_EXPR, a);
	}

	void exprDot(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_DOT, h.next());
				a.setRight(new fId((fNamedToken) h.accept(T_ID)));
				break;
			}
			default:
				throw new RuntimeException("Dot in unexpected place: " + a.astLastNKnd());
		}
	}

	AstProdSubTreeN expr(Ast a) {
		if (a == null) a = new Ast();
		boolean isPlusMinus;
		loop:
		while (true) {
			isPlusMinus = false;
			switch (h.tKnd()) {
				case T_IF: {
					exprIF(a);
					break loop;
				}
				case T_WHILE: {
					a.setRight(exprWhile(a));
					break loop;
				}
				case T_FOR: {
					a.setRight(exprFor(a));
					break loop;
				}
				case T_TRY: {
					a.setRight(exprTry(a));
					break loop;
				}
				case T_THROW: {
					a.setRight(exprThrow(a));
					break loop;
				}
				case T_RETURN: {
					a.setRight(exprReturn(a));
					break loop;
				}

				case T_MATCH: {
					h.next();
					h.accept(fTokenKind.T_LCURL);
					caseClauses();
					h.accept(fTokenKind.T_RCURL);
					break loop;
				}
				case T_LBRACKET: {
					exprLBracket(a); //typeArgs
					break loop;
				}
				case T_LPAREN: {// function() ArgumentExprs
					exprLParen(a);
					if (h.isLa(0, fTokenKind.T_FAT_ARROW)) {
						h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, h.next());
						a.setRight(expr(null));
						break loop;
					}
					continue;
				}
				case T_FAT_ARROW: {
					exprFatArrow(a);
					continue;
				}
				case T_ASSIGN: {
					exprAssign(a);
					continue;
				}
				case T_NEW: {
					a.setRight(exprNEW());
					continue;
				}
				case T_NL: {
					if (!h.isLa(1, T_LCURL)) {
						break loop;
					}
					h.next();
					// fall through
				}
				case T_LCURL: {
					exprLCURL(a);
					continue;
				}
				case T_DOT: {
					exprDot(a);
					continue;
				}
				case T_PLUS: case T_MINUS: {
					isPlusMinus = true;
					//fall through
				}
				case T_TILDE: case T_EXCLAMATION: {
					if (a.astLastNKnd() == AstNodKind.AST_ROOT_OPERATOR || a.astLastNKnd() == AstNodKind.AST_OPERATOR) {
						switch (h.lookAhead(1).kind) {
							case T_NEW: case T_LCURL: case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: case T_UNDERSCORE:
							case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE: case T_NULL: {
								h.next(); continue; //prefix operator
							}
							default:
								throw new RuntimeException("Unexpected token after prefix operator: " + h.getToken());
						}
					} else if (!isPlusMinus) {
						throw new RuntimeException("Unexpected token after prefix operator: " + h.getToken());
					}
					//fall through
				}
				case T_STAR: case T_FORWARD_SLASH: case T_PERCENT: {
					exprMathOper(a);
					continue;
				}
				case T_LT: case T_LTE: case T_GT: case T_GTE: case T_EQUAL: case T_NOT_EQUAL: {
					exprRelationalOper(a);
					continue;
				}
				case T_LOGICAL_AND: case T_LOGICAL_OR:{
					exprLogicalOper(a);
					continue;
				}
				case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE: case T_NULL: {
					exprLit(a);
					continue;
				}
					/*
					  Expr :: (Bindings | ['implicit'] (id|'_')) => Expr
					  ResultExpr :: (Bindings | ['implicit'] (id|'_')) [':' CompoundType] => Block
					 */
				case T_IMPLICIT: {
					if (h.isLa(1, T_UNDERSCORE, T_ID)) {
						h.next();
						if (h.isTkUnderscore()) {
							exprUnderscore(a);
						} else {
							exprTID(a, false);
						}
						if (h.isTkColon()) {
							h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
							a.setRight(type());
						}
						continue;
					}
					throw new RuntimeException("Unexpected token after 'implicit': " + h.getToken());
				}
				case T_UNDERSCORE: {
					exprUnderscore(a);
					if (h.isTkColon()) {  // Binding ::= (id | '_') [':' Type]
						h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
						a.setRight(type());
					}
					continue;
				}
				case T_ID: {
					exprTID(a, false);
					if (h.isTkColon()) {  // Binding ::= (id | '_') [':' Type]
						h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
						a.setRight(type());
					}
					continue;
				}
				case T_THIS: case T_SUPER: {
					exprTID(a, false);
					if (a.isContinue()) continue;
					break loop;
				}
				default:
					break loop;
			}
		}
		return new AstProdSubTreeN(GrmPrd.EXPR, a);
	}

	fWhile exprWhile(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(T_WHILE);
				h.accept(fTokenKind.T_LPAREN);
				fWhile ff = new fWhile(expr(null));
				h.accept(T_RPAREN);
				h.skipNL();
				ff.setWhileBody(expr(null));
				return ff;
			}
			default:
				throw new RuntimeException("While in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprIF(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(fTokenKind.T_IF);
				int sz = h.pushNLEnabled(false);
				h.accept(fTokenKind.T_LPAREN);
				fIf ff = new fIf(expr(null));
				h.popNLEnabled(sz, false);
				h.accept(fTokenKind.T_RPAREN);
				h.skipAllNLs();
				ff.setIfBody(expr(null));
				h.skipSemi();
				if (h.isTkElse()) {
					h.next();
					ff.setElseBody(expr(null));
				}
				a.setRight(ff);
			}
			default:
				break; // for (i <- l1; j <- l2 if i + j > 10) {
		}
	}

	fTry exprTry(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(T_TRY);
				fTry tr = new fTry(expr(null));
				if (h.isTkCatch()) {
					h.next();
					tr.setCatchBlock(expr(null));
				}
				if(h.isTkFinally()){
					h.next();
					tr.setFinallyBlock(expr(null));
				}
				return tr;
			}
			default:
				throw new RuntimeException("Try in unexpected place: " + a.astLastNKnd());
		}
	}

	fThrow exprThrow(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(T_THROW);
				return new fThrow(expr(null));
			}
			default:
				throw new RuntimeException("Throw in unexpected place: " + a.astLastNKnd());
		}
	}

	fReturn exprReturn(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(T_RETURN);
				fReturn ret = new fReturn();
				switch (h.tKnd()) {
					case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN:
					case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: case T_LCURL: case T_NEW: {
						ret.setExpr(expr(null));
						break;
					}
					default:
						break;
				}
				return ret;
			}
			default:
				throw new RuntimeException("Return in unexpected place: " + a.astLastNKnd());
		}
	}

	fFor exprFor(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				h.accept(fTokenKind.T_FOR);
				fFor f4 = null;
				switch (h.tKnd()) {
					case T_LPAREN: {
						int sz = h.pushNLEnabled(false);
						h.accept(T_LPAREN);
						f4 = new fFor(enumerators());
						h.popNLEnabled(sz, false);
						h.accept(fTokenKind.T_RPAREN);
						break;
					}
					case T_LCURL: {
						int sz = h.pushNLEnabled(true);
						h.accept(T_LCURL);
						f4 = new fFor(enumerators());
						h.popNLEnabled(sz, true);
						h.accept(T_RCURL);
						break;
					}
					default:
						throw new RuntimeException("Expected '(' or '{' after 'for' but found: " + h.getToken());
				}

				if (h.isTkYield()) {
					h.next();
					f4.setYield(true);
				}
				f4.setExpr(expr(null));
				return f4;
			}
			default:
				throw new RuntimeException("For in unexpected place: " + a.astLastNKnd());
		}
	}

	List<fGenerator> enumerators() {
		return generators();
	}

	List<fGenerator> generators() {
		List<fGenerator> gens = new ArrayList<>();
		AstProdSubTreeN p1 = null;
		outerLoop:
		while (true) {
			boolean isCase = false;
			if (h.isTkCase()) {
				h.next();
				isCase = true;
			}
			if (p1 == null) {
				p1 = pattern1();
			}
			fGenerator g = new fGenerator(p1, isCase);
			gens.add(g);
			h.accept(fTokenKind.T_IN);
			g.setInitExpr(expr(null));
			innerLoop:
			while (true) {
				h.skipSemi();
				switch (h.tKnd()) {
					case T_CASE: {
						continue outerLoop;
					}
					case T_IF: {
						h.next();
						g.setGuard(postfixExpr());
						continue innerLoop;
					}
					case T_UNDERSCORE: case T_ID: case T_THIS: case T_SUPER: case T_LPAREN:
					case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE:
					case T_NULL: {
						p1 = pattern1();
						switch (h.tKnd()) {
							case T_ASSIGN: {
								g.setEndingPattern1(p1);
								h.accept(T_ASSIGN);
								g.setEndingExpr(expr(null));
								continue innerLoop;
							}
							case T_IN: {
								continue outerLoop;
							}
							default:
								throw new RuntimeException("Expected '=' or 'in' but found: " + h.getToken());
						}
					}
					default:
						break outerLoop;
				}
			}
		}
		return gens;
	}

	fStableId path() {
		return stableId(true, false);
	}

	fLiteral literal() {
		switch (h.tKnd()) {
			case T_INT_LIT: {
				return new fLiteral.fIntLit(h.next());
			}
			case T_FLOAT_LIT: {
				return new fLiteral.fFloatLit(h.next());
			}
			case T_STRING_LIT: {
				return new fLiteral.fStringLit(h.next());
			}
			case T_CHAR_LIT: {
				return new fLiteral.fCharLit(h.next());
			}
			case T_TRUE: case T_FALSE: {
				return new fLiteral.fBoolLit(h.next());
			}
			case T_NULL: {
				return new fLiteral.fNullLit(h.next());
			}
			default:
				throw new RuntimeException("Expected literal but found: " + h.getToken());
		}
	}

	fStableId pathWithKwType() {
		return stableId(true, true);
	}

	fStableId stableId(boolean isPath) {
		return stableId(isPath, false);
	}

	fStableId stableId(boolean isPath, boolean withKwType) {
		assert h.tKnd() == fTokenKind.T_ID || h.tKnd() == fTokenKind.T_THIS || h.tKnd() == fTokenKind.T_SUPER : "Expected T_ID, T_THIS or T_SUPER but found: " + h.getToken();

		fStableId sid = new fStableId(isPath);
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_DOT: {
					if (h.isLa(1, T_ID, T_SUPER, T_THIS) || withKwType && h.isLa(1, T_TYPE)) {
						h.next();
						continue;
					}
					break loop;
				}
				case T_ID: {
					sid.addTId((fNamedToken) h.next());
					if (h.isTkDot()) continue;
					break loop;
				}
				case T_THIS: {
					sid.addThis(h.next());
					if (h.isTkDot()) continue;
					break loop;
				}
				case T_SUPER: {
					sid.addSuper(h.next());
					if (h.isTkLBracket()) {
						int n = h.pushNLEnabled(false);
						h.accept(T_LBRACKET);
						sid.addClassQualifier((fNamedToken) h.accept(T_ID));
						h.popNLEnabled(n, false);
						h.accept(T_RBRACKET);
					}
					if (h.isTkDot()) continue;
					break loop;
				}
				default:
					break loop;
			}
		}
		fTokenKind last = sid.getLastTKind();
		if (!isPath) {
			assert last == fTokenKind.T_ID;
		} else {
			assert last == fTokenKind.T_ID || last == fTokenKind.T_THIS;
		}
		return sid;
	}

	void exprLit(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				a.setRight(literal());
				break;
			}
			default:
				throw new RuntimeException("Literal in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprMathOper(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				fLangOperatorKind ok = fLangOperatorKind.getMathOperatorKind(h.getTokenKind());
				h.insertOperator(a, ok, h.next());
				h.skipNL();
				break;
			}
			default:
				throw new RuntimeException("MathOper in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprRelationalOper(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				fLangOperatorKind ok = fLangOperatorKind.getRelationalOperatorKind(h.getTokenKind());
				h.insertOperator(a, ok, h.next());
				h.skipNL();
				break;
			}
			default:
				throw new RuntimeException("RelationalOper in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLogicalOper(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				fLangOperatorKind ok = fLangOperatorKind.getLogicalOperatorKind(h.getTokenKind());
				h.insertOperator(a, ok, h.next());
				h.skipNL();
				break;
			}
			default:
				throw new RuntimeException("LogicalOper in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprUnderscore(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				a.setRight(new fUnderscore(h.next()));
				break;
			}
			case AST_OPERAND:{
				h.insertOperator(a, fLangOperatorKind.O_ETA_EXPANSION, h.getToken());
				a.setRight(new fUnderscore(h.next()));
				break;
			}
			default:
				throw new RuntimeException("Underscore in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprTID(Ast a, boolean isPostfixExpr) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				a.setRight(path());
				break;
			}
			case AST_OPERAND: {
				if (h.isTkId()) {
					if (!isPostfixExpr && h.isTkColon()) {
						h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
						a.setRight(type());
						a.setContinue(false);

					} else if (!isPostfixExpr && h.isTkAssign()) {
						h.insertOperator(a, fLangOperatorKind.O_ASSIGN, h.next());
						a.setRight(expr(a));
						a.setContinue(false);
					} else {
						h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), h.next());
						h.skipNL();
					}
					break;
				}
				// fall through
			}
			default:
				throw new AssertionError("TID in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLParen(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				a.setRight(exprs());
				break;
			}
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_PARENS, h.getToken());
				a.setRight(exprs());
				break;
			}
			default:
				throw new RuntimeException("LParen in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLBracket(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_BRACKETS, h.getToken());
				a.setRight(typeArgs());
				break;
			}
			default:
				throw new RuntimeException("LBracket in unexpected place: " + a.astLastNKnd());
		}
	}

	fTemplateBody exprNEW() {
		h.accept(fTokenKind.T_NEW);
		switch (h.tKnd()) {
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
		if (h.isTkLCurl()) {
			new fClassTemplate(templateBody(), cp, isTrait);
		}
		return new fClassTemplate(cp, isTrait);
	}

	AstProdSubTreeN block() {
		Ast a = new Ast();
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_CASE: {
					if (!h.isLa(1, T_CLASS, T_OBJECT)) {
						break loop;
					}
				}
				//fall through
				case T_IMPORT: case T_IMPLICIT: case T_LAZY: case T_ABSTRACT: case T_FINAL: case T_SEALED:
				case T_VAL: case T_VAR: case T_DEF: case T_TYPE: case T_CLASS: case T_OBJECT: case T_TRAIT:
				case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN: case T_NEW:
				case T_LCURL: case T_LPAREN: case T_ID: case T_THIS: case T_SUPER: case T_INT_LIT: case T_FLOAT_LIT:
				case T_STRING_LIT: case T_CHAR_LIT: case T_NULL: case T_TRUE: case T_FALSE: {
					a.setRight(blockStat());
					h.skipSemi();
					h.insertStmtSepOper(a);
					continue;
				}
				default:
					break loop;
			}
		}
		return new AstProdSubTreeN(GrmPrd.BLOCK, a);
	}

	fTemplateBody templateBody() {
		Ast a = new Ast();
		if (h.isTkNL()) h.next();
		int sz = h.pushNLEnabled(true);
		h.accept(fTokenKind.T_LCURL);
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_IMPORT: case T_ABSTRACT: case T_FINAL: case T_SEALED: case T_IMPLICIT: case T_LAZY:
				case T_OVERRIDE: case T_PROTECTED: case T_PRIVATE:
				case T_VAL: case T_VAR: case T_DEF: case T_TYPE: case T_CLASS: case T_OBJECT: case T_TRAIT:
				case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN: case T_NEW:
				case T_LCURL: case T_LPAREN: case T_ID: case T_THIS: case T_SUPER: case T_INT_LIT: case T_FLOAT_LIT:
				case T_STRING_LIT: case T_CHAR_LIT: case T_NULL: case T_TRUE: case T_FALSE: {
					a.setRight(templateStat());
					h.insertStmtSepOper(a);
					h.skipSemi();
					continue;
				}
				default:
					break loop;
			}
		}
		h.popNLEnabled(sz, true);
		h.accept(fTokenKind.T_RCURL);
		return new fTemplateBody(new AstProdSubTreeN(GrmPrd.TEMPLATE_BODY, a));
	}


	AstProdSubTreeN templateStat() {
		return blockOrTemplateStat(GrmPrd.TEMPLATE_STAT);
	}

	AstProdSubTreeN blockStat() {
		return blockOrTemplateStat(GrmPrd.BLOCK_STAT);
	}

	AstOperandNod classObjectDef(boolean isCase, fModifiers mods) {
		switch (h.tKnd()) {
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
		h.skipNL();
		while (h.isTkLParen()) {
			if (h.isLa(1, fTokenKind.T_IMPLICIT)) {
				cpcs.setImplicitParams(classParamClause(true));
				break;
			}
			cpcs.addParams(classParamClause(false));
		}
		return cpcs;
	}

	List<fClassParam> classParamClause(boolean isImplicit) {
		List<fClassParam> params = new ArrayList<>();
		int sz = h.pushNLEnabled(false);
		h.accept(T_LPAREN);
		if (isImplicit) {
			h.accept(T_IMPLICIT);
		}
		if (!h.isTkRParen()) {
			while (true) {
				params.add(classParam());
				if (h.isTkComma()) {
					h.next();
					continue;
				}
				break;
			}
		}
		h.popNLEnabled(sz, false);
		h.accept(T_RPAREN);
		return params;
	}

	fClassParam classParam() {
		fClassParam p = new fClassParam();
		//Modifier
		switch (h.tKnd()) {
			case T_ABSTRACT: case T_FINAL: case T_SEALED: case T_LAZY: case T_IMPLICIT:
			case T_PRIVATE: case T_PROTECTED: case T_OVERRIDE: {
				p.setModifiers(modifiers());
				break;
			}
			default:
				break;
		}

		switch (h.tKnd()) {
			case T_VAL: {
				p.setValVar(fValVar.VAL);
				h.next();
				break;
			}
			case T_VAR: {
				p.setValVar(fValVar.VAR);
				h.next();
				break;
			}
			default:
				p.setValVar(fValVar.NONE);
				break;
		}
		p.setIdentifier((fNamedToken) h.accept(T_ID));
		if (h.isTkColon()) {
			h.accept(T_COLON);
			p.setParamType(paramType(false));
		}
		if (h.isTkAssign()) {
			h.next();
			p.setDefaultValue(expr(null));
		}
		return p;
	}

	fClassParents classParents(boolean isTrait) {
		fClassParents parents = new fClassParents(classConstructor(isTrait));
		while (h.isTkWith()) {
			h.next();
			parents.addWithType(simpleType());
		}
		return parents;
	}

	fClassConstructor classConstructor(boolean isTrait) {
		fClassConstructor cc = new fClassConstructor(simpleType());
		if (!isTrait && h.isTkLParen()) {
			cc.setArgExprs(exprs());
		}
		return cc;
	}

	fTraitDef traitDef(fModifiers mods) {
		h.accept(fTokenKind.T_TRAIT);
		fTraitDef trait = new fTraitDef((fNamedToken) h.next());
		if (h.isTkLBracket()) {
			trait.setTypeParams(variantTypeParams());
		}
		trait.setExtendsTemplate(classExtends(true));
		return trait;
	}

	fClassDef classDef(boolean isCase) {
		h.accept(fTokenKind.T_CLASS);
		fClassDef cls = new fClassDef((fNamedToken) h.next(), isCase);
		if (h.isTkLBracket()) {
			cls.setTypeParams(variantTypeParams());
		}
		cls.setClassParamClauses(classParamClauses());
		cls.setExtendsTemplate(classExtends(false));
		return cls;
	}

	fTemplateBody classExtends(boolean isTrait) {

		switch (h.tKnd()) {
			case T_NL: case T_LCURL: {
				return templateBody();
			}
			case T_EXTENDS: {
				h.next();
				switch (h.tKnd()) {
					case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: {
						return classTemplate(isTrait);
					}
					case T_NL: case T_LCURL: {
						return templateBody();
					}
					default:
						throw new RuntimeException("Expected class template or '{' after 'extends' but found: " + h.getToken());
				}
			}
			default:
				//pass
		}
		return null;
	}

	fObject objectDef(boolean isCase) {
		h.accept(fTokenKind.T_OBJECT);
		fObject obj = new fObject((fNamedToken) h.next(), isCase);
		obj.setExtendsTemplate(classExtends(false));
		return obj;
	}

	fFun funDef(fModifiers mods) {
		h.accept(fTokenKind.T_DEF);
		switch (h.tKnd()) {
			case T_ID: {
				return namedFun(mods);
			}
			case T_THIS: {
				return thisFun(mods);
			}
			default:
				throw new RuntimeException("Expected 'def' followed by identifier or 'this' but found: " + h.getToken());
		}
	}

	fNamedFun namedFun(fModifiers mods) {
		fNamedFun fun = new fNamedFun(mods, funSig());
		if (h.isTkColon()) {
			h.next();
			fun.setReturnType(type());
		}
		if (h.isTkAssign()) {
			h.next();
			fun.setBody(expr(null));
		} else if (h.isTkLCurl()) {
			int sz = h.pushNLEnabled(true);
			h.accept(T_LCURL);
			fun.setBody(block());
			h.popNLEnabled(sz, true);
			h.accept(T_RCURL);
		}
		return fun;
	}

	fThisFun thisFun(fModifiers mods) {
		h.accept(T_THIS);
		fThisFun fun = new fThisFun(mods);
		fun.setParamClauses(paramClauses());
		if (h.isTkAssign()) {
			h.next();
			fConstrBlock cb;
			if (h.isTkTHIS()) {
				h.next();
				cb = new fConstrBlock();
				cb.setArgExprs(exprs());
			} else if (h.isTkLCurl()) {
				cb = constrBlock();
			} else {
				throw new RuntimeException("Expected 'this' or '{' after '=' in function definition but found: " + h.getToken());
			}
			fun.setConstrBlock(cb);

		} else {

			fun.setConstrBlock(constrBlock());
		}
		return fun;
	}

	fConstrBlock constrBlock() {
		fConstrBlock cb = new fConstrBlock();
		h.accept(T_LCURL);
		if (h.isTkTHIS()) {
			h.next();
			cb.setArgExprs(exprs());
		}
		while (true) {
			h.skipSemi();
			if (!h.isTkRParen()) {
				cb.addBlockStat(blockStat());
			} else {
				break;
			}
		}
		h.accept(T_RCURL);
		return cb;
	}

	fFunSig funSig() {
		fFunSig fs = new fFunSig((fNamedToken) h.next());
		if (h.isTkLBracket()) {
			fs.setTypeParams(funTypeParams());
		}
		fs.setParamClauses(paramClauses());
		return fs;
	}

	fTypeDef typeDef() {
		h.accept(fTokenKind.T_TYPE);
		fTypeDef t = new fTypeDef((fNamedToken) h.next());
		if (h.isTkLBracket()) {
			t.setTypeParams(variantTypeParams());
		}
		h.accept(T_ASSIGN);
		t.setAssignedType(type());
		return t;
	}

	fParamClauses paramClauses() {

		fParamClauses pcs = new fParamClauses();
		while (h.isTkLParen()) {
			if (h.isLa(1, fTokenKind.T_IMPLICIT)) {
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
		if (!h.isTkRParen()) {
			if (isImplicit) {
				h.accept(fTokenKind.T_IMPLICIT);
			}
			while (true) {
				params.add(param());
				if (h.isTkComma()) {
					h.next(); continue;
				}
				break;
			}
		}
		h.accept(fTokenKind.T_RPAREN);
		return params;
	}

	fParam param() {
		fParam p = new fParam((fNamedToken) h.next());
		if (h.isTkColon()) {
			h.next();
			p.setParamType(paramType(false));
		}
		if (h.isTkAssign()) {
			h.next();
			p.setDefaultValue(expr(null));
		}
		return p;
	}

	List<fTypeParam> funTypeParams() {
		List<fTypeParam> params = new ArrayList<>();
		int sz = h.pushNLEnabled(false);
		h.accept(fTokenKind.T_LBRACKET);
		while (true) {
			params.add(typeParam());
			if (h.isTkComma()) {
				h.next();
				continue;
			}
			break;
		}
		h.popNLEnabled(sz, false);
		h.accept(T_RBRACKET);
		return params;
	}


	List<fVariantTypeParam> variantTypeParams() {
		List<fVariantTypeParam> params = new ArrayList<>();
		h.accept(fTokenKind.T_LBRACKET);
		while (true) {
			params.add(variantTypeParam());
			if (h.isTkComma()) {
				h.next();
				continue;
			}
			break;
		}
		h.accept(T_RBRACKET);
		return params;
	}

	fTypeParam typeParam() {
		fTypeParam p = new fTypeParam((fNamedToken) h.next());
		if (h.isTkLBracket()) {
			p.setVariantTypeParams(variantTypeParams());
		}
		if (h.isTkLowerBound()) {
			p.setLowerBound(type());
		}
		if (h.isTkUpperBound()) {
			p.setUpperBound(type());
		}
		if (h.isTkColon()) {
			p.setType(type());
		}

		return p;
	}

	fVariantTypeParam variantTypeParam() {
		fVariantTypeParam p = new fVariantTypeParam((fNamedToken) h.next());
		if (h.isTkPlus()) {
			h.next();
			p.setVariance(fVariantTypeParam.fVariance.VARIANT);
		} else if (h.isTkMinus()) {
			h.next();
			p.setVariance(fVariantTypeParam.fVariance.INVARIANT);
		}
		if (h.isTkLowerBound()) {
			p.setLowerBound(type());
		}
		if (h.isTkUpperBound()) {
			p.setUpperBound(type());
		}
		if (h.isTkColon()) {
			p.setType(type());
		}

		return p;
	}

	fValue varDef(fModifiers mods) {
		// patDef() + "ids: Type = _"
		return patDef(false, mods);
	}


	AstProdSubTreeN patterns() {
		Ast a = new Ast();
		while (true) {
			a.setRight(pattern());
			if (h.isTkComma()) {
				h.insertOperator(a, fLangOperatorKind.O_COMMA, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PATTERNS, a);
	}

	AstProdSubTreeN pattern() {
		Ast a = new Ast();
		while (true) {
			a.setRight(pattern1());
			if (h.isTkPipe()) {
				h.insertOperator(a, fLangOperatorKind.O_PIPE, h.next());
				continue;
			}
			break;
		}
		return new AstProdSubTreeN(GrmPrd.PATTERN, a);
	}

	AstProdSubTreeN pattern1() {
		Ast a = new Ast();
		if(h.isLa(1, T_COLON)){ //Multiple parse tree possible: SimplePattern {id [nl] SimplePattern}
			switch(h.tKnd()){
				case T_UNDERSCORE: {
					a.setRight(new fUnderscore(h.next()));
					h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
					a.setRight(type());
					break;
				}
				case T_ID: {
					a.setRight(stableId(false));
					h.insertOperator(a, fLangOperatorKind.O_COLON, h.next());
					a.setRight(type());
					break;
				}
				default:
					throw new RuntimeException("Pattern1 in unexpected place: " + h.getToken());
			}
		} else {
			a.setRight(pattern2());
		}
		return new AstProdSubTreeN(GrmPrd.PATTERN_1, a);
	}

	fValue patDef(boolean isVal, fModifiers mods) {
		fValue value;
		if(isVal) value = new fValueDef(mods);
		else  value = new fValueDecl(mods);

		while (true) {
			value.addName(pattern2());
			if (h.isTkComma()) {
				h.next(); continue;
			}
			break;
		}
		if (h.isTkColon()) {
			h.next();
			value.setType(type());
		}
		if(h.isTkAssign()){
			h.next();
			value.setAssignExpr(expr(null));
		}
		return value;
	}

	AstProdSubTreeN pattern2() {
		Ast a = new Ast();
		switch (h.tKnd()) {
			case T_ID: {
				if (h.isLa(1, T_AT)) {
					a.setRight(new fStableId(false));
					h.insertOperator(a, fLangOperatorKind.O_AT, h.next());
					a.setRight(pattern3());
					return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
				}
				//fall through
			}
			case T_UNDERSCORE: case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT:
			case T_TRUE: case T_FALSE: case T_NULL:
			case T_THIS: case T_SUPER: case T_LPAREN: {
				a.setRight(pattern3());
				return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
			}
			default:
				throw new RuntimeException("Pattern in unexpected place: " + h.getToken());
		}
	}

	AstProdSubTreeN pattern3() {
		Ast a = new Ast();
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_UNDERSCORE:{
					h.next();
					continue ;
				}
				case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE: case T_NULL: {
					a.setRight(literal());
					continue;
				}
				case T_ID: case T_THIS: case T_SUPER: {
					pattern3Id(a);
					if(a.isContinue()) continue;
					break loop;
				}
				case T_LPAREN: {
					int sz = h.pushNLEnabled(false);
					h.accept(fTokenKind.T_LPAREN);
					h.insertOperator(a, fLangOperatorKind.O_PARENS, h.getToken());
					a.setRight(patterns());
					h.popNLEnabled(sz, false);
					h.accept(fTokenKind.T_RPAREN);
					continue;
				}
				default:
					break loop;
			}
		}
		return new AstProdSubTreeN(GrmPrd.SUBTREE, a);
	}

	void pattern3Id(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {
				a.setRight(stableId(false));
				break;
			}
			case AST_OPERAND: {
				if(h.isTkTID()){
					h.insertOperator(a, fLangOperatorKind.getIdSymbolicAssoc(h.getAsNamedToken().isRightAssociative()), h.next());
				} else {
					a.setContinue(false);
				}
				break;
			}
			default:
				throw new RuntimeException("Pattern3Id in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprFatArrow(Ast a) {
		switch (a.astLastNKnd()){
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_FAT_ARROW, h.next());
				a.setRight(expr(null));
				break;
			}
			default:
				throw new RuntimeException("FatArrow in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprAssign(Ast a) {
		switch (a.astLastNKnd()){
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_ASSIGN, h.next());
				a.setRight(expr(null));
				break;
			}
			default:
				throw new RuntimeException("Assign in unexpected place: " + a.astLastNKnd());
		}
	}

	void exprLCURL(Ast a) {
		int sz = h.pushNLEnabled(true);
		h.accept(T_LCURL);
		switch (a.astLastNKnd()) {
			case AST_OPERAND: {
				h.insertOperator(a, fLangOperatorKind.O_CURLY_BRACES, h.getToken());
				//fall through
			}
			case AST_ROOT_OPERATOR: case AST_OPERATOR: {

				if (h.tKnd() == fTokenKind.T_CASE) {
					a.setRight(caseClauses());
				} else {
					a.setRight(block());
				}
				break;
			}
			default:
				throw new RuntimeException("LCURL in unexpected place: " + a.astLastNKnd());
		}
		h.popNLEnabled(sz, true);
		h.accept(T_RCURL);
	}

	fCaseClauses caseClauses() {
		List<fCaseClauses.fCaseClause> clauses = new ArrayList<>();
		while (h.isTkCase()) {
			h.next();
			fCaseClauses.fCaseClause cc = new fCaseClauses.fCaseClause(pattern());
			if (h.isTkIF()) {
				h.next();
				cc.setGuard(postfixExpr());
			}
			h.accept(T_FAT_ARROW);
			cc.setBlock(block());
		}
		return new fCaseClauses(clauses);
	}

	AstProdSubTreeN exprs() {
		Ast box = new Ast();
		Ast curr = null;
		int lparSz = 0;
		assert h.isTkLParen();
		int sz = 0;
		loop1:
		while (true) {
			switch (h.tKnd()) {
				case T_LPAREN: {
					sz = h.pushNLEnabled(false);
					h.accept(T_LPAREN);
					lparSz++;
					continue;
				}
				default:
					break loop1;
			}
		}

		int prevLParSz = lparSz;
		while (true) {
			box.setRight(expr(curr));

			while(h.isTkComma()){
				h.insertOperator(box, fLangOperatorKind.O_COMMA, h.next());
				box.setRight(expr(null));
			}

			loop2:
			while (true) {
				switch (h.tKnd()) {
					case T_RPAREN: {
						h.popNLEnabled(sz--, false);
						h.accept(T_RPAREN);
						lparSz--;
						if (lparSz == 0) {
							break loop2;
						}
						continue;
					}
					default:
						break loop2;
				}
			}
			assert lparSz >= 0;
			assert prevLParSz > lparSz: "Unclosed right parentheses";
			prevLParSz = lparSz;
			if(lparSz == 0) {
				return new AstProdSubTreeN(GrmPrd.EXPRS_OR_BINDINGS, box);
			}
			curr = box;
			box = new Ast();
		}
	}


	fImport importClause() {
		h.accept(fTokenKind.T_IMPORT);
		fImport im = new fImport();
		while (true) {
			fImport.fImportExpr imEx = new fImport.fImportExpr(stableId(false));
			if(h.tKnd() == T_DOT){
				h.next();
				switch (h.tKnd()){
					case T_UNDERSCORE: {
						h.next();
						break;
					}
					case T_LCURL: {
						imEx.setSelectors(importSelectors());
						break;
					}
					default:
						throw new RuntimeException("Import in unexpected place: " + h.tKnd());
				}
			}
			im.addImportExpr(imEx);
			if (h.isTkComma()) {
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
			fNamedToken from = (fNamedToken) h.accept(T_ID);
			fNamedToken to = null;
			if (h.isTkFatArrow()) {
				h.next();
				to = (fNamedToken) h.accept(T_ID);
			}
			selectors.add(new fImport.fImportSelector(from, to));
			if (h.isTkComma()) {
				h.next();
				continue;
			}
			break;
		}
		h.accept(T_RCURL);
		return selectors;
	}

	fModifiers modifiers() {
		fModifiers mods = new fModifiers();
		loop:
		while (true) {
			switch (h.tKnd()) {
				case T_OVERRIDE:{
					mods.addModifier(h.next());
					break loop;
				}
				case T_ABSTRACT: case T_FINAL: case T_SEALED: case T_IMPLICIT: case T_LAZY:
				{
					mods.addModifier(h.next());
					continue;
				}
				case T_PROTECTED: case T_PRIVATE:{
					h.next();
					if(h.isTkLBracket()){
						int sz = h.pushNLEnabled(false);
						h.accept(T_LBRACKET);
						switch (h.tKnd()) {
							case T_ID: case T_THIS: {
								mods.addSpecialModifier(h.next());
								break;
							}
							default:
								throw new RuntimeException("Expected id or 'this' in access modifier but found: " + h.getToken());
						}
						h.popNLEnabled(sz, false);
						h.accept(T_RBRACKET);
					} else {
						mods.addModifier(h.next());
					}
					break loop;
				}
				default:
					break loop;
			}
		}
		return mods;
	}

	AstProdSubTreeN blockOrTemplateStat(GrmPrd prd) {
		final Ast a = new Ast();
		if(h.isTkImport()){

			a.setRight(importClause());

		} else {
			fModifiers mods = null;
			switch (h.tKnd()) {
				case T_ABSTRACT: case T_FINAL: case T_SEALED: case T_IMPLICIT: case T_LAZY:
				case T_OVERRIDE: case T_PROTECTED: case T_PRIVATE: {
					mods = modifiers();
					break;
				}
				default:
					break;
			}
			boolean isCase = false;
			switch (h.tKnd()) {

				case T_CASE: {
					if (h.isLa(1, T_CLASS, T_OBJECT)) {
						isCase = true;
						h.next();
						//fall through
					} else {
						break;
					}
				}
				case T_CLASS: case T_OBJECT: {
					a.setRight(classObjectDef(isCase, mods));
					break;
				}
				case T_TRAIT: {
					a.setRight(traitDef(mods));
					break;
				}
				case T_VAL: {
					h.next();
					a.setRight(patDef(true, mods));
					break;
				}
				case T_VAR: {
					h.next();
					a.setRight(varDef(mods));
					break;
				}
				case T_DEF: {
					a.setRight(funDef(mods));
					break;
				}
				case T_TYPE: {
					a.setRight(typeDef());
					break;
				}
				case T_IF: case T_WHILE: case T_FOR: case T_TRY: case T_THROW: case T_RETURN:
				case T_ID: case T_THIS: case T_SUPER: case T_LPAREN: case T_LCURL: case T_NEW:
				case T_INT_LIT: case T_FLOAT_LIT: case T_STRING_LIT: case T_CHAR_LIT: case T_TRUE: case T_FALSE:
				case T_NULL: {
					a.setRight(expr(null));
					break;
				}
				default:
					break;
			}
		}
		return new AstProdSubTreeN(prd, a);
	}

	fPackage packageClause() {
		h.accept(T_PACKAGE);
		return new fPackage(ids(true));
	}

	List<fPackage> packages() {
		List<fPackage> ps = new ArrayList<>();
		while (h.isTkPackage()) {
			ps.add(packageClause());
			h.skipSemi();
		}
		return ps;
	}

	public fCompilationUnit compilationUnit() {

		fCompilationUnit cu = new fCompilationUnit();
		if (h.isTkPackage()) {
			cu.setPackages(packages());
		}
		loop:
		while (true) {

			if(h.isTkImport()){

				cu.addImport(importClause());

			} else {

				fModifiers mods = null;
				switch (h.tKnd()){
					case T_ABSTRACT: case T_FINAL: case T_SEALED: case T_IMPLICIT: case T_LAZY:
					case T_OVERRIDE: case T_PROTECTED: case T_PRIVATE: {
						mods = modifiers();
						break;
					}
					default:
						break;
				}

				boolean isCase = false;

				switch (h.tKnd()) {

					case T_CASE: {
						if (h.isLa(1, T_CLASS, T_OBJECT)) {
							isCase = true;
							h.next();
							//fall through
						} else {
							break loop;
						}
					}
					case T_CLASS: case T_OBJECT: {
						cu.addStatement(classObjectDef(isCase, mods));
						break;
					}
					case T_TRAIT: {
						cu.addStatement(traitDef(mods));
						break;
					}
					default:
						break loop;
				}
			}
			h.skipSemi();
		}
		return cu;
	}
}
