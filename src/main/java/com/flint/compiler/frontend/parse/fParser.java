package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.leaves.node.IdLeafNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.subtree.AstProdSubTreeN;
import com.flint.compiler.frontend.lang.grammar.GrmPrd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;
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
		return new AstProdSubTreeN(GrmPrd.EXPRS, a.rootOp);
	}



	AstProdSubTreeN expr(Ast z) {
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_RPAREN:
					break loop;
				case T_LPAREN:
					exprLeftParen(z);
					continue ;
				case T_ID: case T_THIS: case T_SUPER:
					exprTID(z);
					continue;

				default:
					throw new AssertionError("Unexpected token: " + h.getToken());
			}
		}

		return new AstProdSubTreeN(GrmPrd.EXPR, z.rootOp);
	}

	void exprTID(Ast z) {
		switch (z.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER:
				z.setRight(new IdLeafNod((NamedToken)h.next()));
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
					int n = h.skipRPar(lparSz); assert n > 0: "unmatched left par"; lparSz -= n;
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
