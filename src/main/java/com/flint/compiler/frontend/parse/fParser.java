package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.leaves.AstProdSubTreeN;
import com.flint.compiler.frontend.ast.nodes.leaves.AstSubTreeNod;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.utils.Ast;

public class fParser {

	private final ParseHelp h;

	public fParser(fLexer lexer) {
		h = new ParseHelp(lexer);
	}

	AstProdSubTreeN exprs(Ast a) {
		a.setRight(expr(a));
		while (h.isTkComma()) {
			h.next();
			h.insertEntitySep(a.astLastN());
			a.setRight(expr(a));
		}
		return new AstProdSubTreeN(GrmPrd.EXPRS, a);
	}



	AstProdSubTreeN expr(Ast a) {
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_RPAREN:
					break loop;

				case T_LPAREN:
					exprLeftParen(a);
					break;

				default:
					throw new AssertionError("Unexpected token: " + h.getToken());
			}
		}

		return new AstProdSubTreeN(GrmPrd.EXPR, a);
	}

	void exprTID(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER:
				break;
			case AST_ID_LEAF:
				break;

		}
	}




	void exprLeftParen(Ast z) {
		switch (z.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER: {
				int lparSz = h.skipLPar(); assert lparSz > 0;
				Ast y = new Ast();
				while(true) {
					AstProdSubTreeN x = exprs(y);
					int n = h.skipRPar(lparSz); assert n > 0; lparSz -= n;
					if(lparSz == 0){
						z.setRight(x);
						return;
					}
					y = new Ast();
					y.setRight(x);
				}
			}
			default:
				throw new AssertionError("LParen in unexpected place");
		}
	}

}
