package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.leaves.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.leaves.AstSubTreeNod;
import com.flint.compiler.frontend.lang.grammar.GProd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.utils.Ast;

public class fParser {

	private final ParseHelp h;

	public fParser(fLexer lexer) {
		h = new ParseHelp(lexer);
	}

	AstProdSubTreeN exprs(Ast a) {
//		AstPtr pa = new AstPtr();
		a.setRight(expr());
		while (h.isTkComma()) {
			h.next();
			h.insertEntitySep(a.astLastN());
			a.setRight(expr());
		}
		return new AstProdSubTreeN(GProd.EXPRS, a);
	}



	AstProdSubTreeN expr() {
		Ast a = new Ast();
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_RPAREN:
					a = exprRightParen(a);
					if(a.lparSz == 0) {
						break loop;
					}
					break;

				case T_LPAREN:
					a = exprLeftParen(a);
					break;

//				case T_ID: case T_THIS: case T_SUPER:
//					a.setRight(h.accept(fTokenKind.T_ID));
//					break;

				default:
					throw new AssertionError("Unexpected token: " + h.getToken());
			}
		}

		return new AstProdSubTreeN(GProd.EXPR, a);
	}

	void exprTID(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER:
				break;
			case AST_ID_LEAF:
				break;

		}
	}

	Ast exprRightParen(Ast a) {
		assert a.parentAst != null && a.parentAst.lparSz > 0;
		a.parentAst.lparSz = h.skipRPar(a.parentAst.lparSz);
		a.parentAst.setRight(new AstSubTreeNod(a));
		return a.parentAst;
	}

	Ast exprLeftParen(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER: {
				a.lparSz = h.skipLPar();
				assert a.lparSz > 0;
				AstProdSubTreeN tree = exprs(a);
				a.lparSz -= h.skipRPar(a.lparSz);
				if(a.lparSz == 0) {
					a.setRight(tree);
					return a;
				}
				Ast outer = new Ast();
				outer.parentAst = a;
				outer.lparSz = a.lparSz;
				return outer;
			}
			default:
				throw new AssertionError("LParen in unexpected place");
		}
	}

}
