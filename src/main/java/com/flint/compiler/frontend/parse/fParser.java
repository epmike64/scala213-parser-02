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

	AstProdSubTreeN exprs() {
		Ast a = new Ast();
		a.setRight(expr());
		while (h.isTkComma()) {
			h.next();
			h.insertEntitySep(a.astLastN());
			a.setRight(expr());
		}
		return new AstProdSubTreeN(GrmPrd.EXPRS, a.rootOp);
	}



	AstProdSubTreeN expr() {
		Ast a = new Ast();
		loop:
		while (true) {
			switch (h.TKnd()) {
				case T_RPAREN:
					a = exprRightParen(a);
					if(a.astParClosure == null) {
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

		return new AstProdSubTreeN(GrmPrd.EXPR, a.rootOp);
	}

	void exprTID(Ast a) {
		switch (a.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER:
				break;
			case AST_ID_LEAF:
				break;

		}
	}

	Ast exprRightParen(Ast y) {
		assert  y.astParClosure != null;
		int lparSz = h.skipRPar(y.astParClosure.lparSz);
		if(lparSz == 0){
			// V = SubTree( Y / D )
			// Z = K *  V
			Ast z = y.astParClosure.ast;
			z.setRight(new AstSubTreeNod(y.rootOp));
			return z;
		}
		// SubTree V =   ( Y / D )
		// Z = K * V
		Ast v = new Ast();
		v.setRight(new AstSubTreeNod(y.rootOp));
		v.astParClosure = new Ast.ParenClosure( /* z */ y.astParClosure.ast, lparSz);
		return v;
	}

	Ast exprLeftParen(Ast z) {
		switch (z.astLastNKnd()) {
			case AST_ROOT: case AST_ID_OPER: {
				int lparSz = h.skipLPar();
				assert lparSz > 0;
				AstProdSubTreeN x = exprs();
				lparSz -= h.skipRPar(lparSz);
				if(lparSz == 0) {
					// X = (A + B)
					// Z = K * X
					z.setRight(x);
					return z;
				}
				// Z = K * (((A + B) * C) / D)
				// X = SubTree(A + B)
				// Y = SubTree( X * C )
				// V = SubTree( Y / D )
				// Z = K * V
				Ast y = new Ast();
				y.setRight(x);
				y.astParClosure = new Ast.ParenClosure(z, lparSz);
				return y;
			}
			default:
				throw new AssertionError("LParen in unexpected place");
		}
	}

}
