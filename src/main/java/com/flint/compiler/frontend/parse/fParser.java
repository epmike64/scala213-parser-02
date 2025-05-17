package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.kinds.AstNodKind;
import com.flint.compiler.frontend.ast.nodes.leaves.AstProdSubTreeN;
import com.flint.compiler.frontend.lang.grammar.GProd;
import com.flint.compiler.frontend.parse.lex.fLexer;
import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.utils.ParseArgs;

public class fParser {

	private final ParseHelp ph;

	public fParser(fLexer lexer) {
		ph = new ParseHelp(lexer);
	}

	AstProdSubTreeN exprs() {
		ParseArgs pa = new ParseArgs();
		pa.setRight(expr());
		while (ph.getTokenKind() == fTokenKind.T_COMMA) {
			ph.next();
			pa.insertEntitySep();
			pa.setRight(expr());
		}
		return new AstProdSubTreeN(GProd.EXPRS, pa.rootOp, pa.getLastAdded());
	}

	AstProdSubTreeN expr() {
		ParseArgs pa = new ParseArgs();
		loop:
		while (true) {
			switch (ph.getTokenKind()) {
				case T_ID:
					pa.setRight(ph.accept(fTokenKind.T_ID));
					break;
				case T_LPAREN:
					ph.accept(fTokenKind.T_LPAREN);
					pa.setRight(exprLeftParen(pa));
					break;
				case T_RPAREN:
					ph.accept(fTokenKind.T_RPAREN);
					break loop;
				case T_COMMA:
					ph.accept(fTokenKind.T_COMMA);
					break loop;
				default:
					throw new AssertionError("Unexpected token: " + ph.getToken());
			}
		}

		return new AstProdSubTreeN(GProd.EXPR, pa.rootOp);
	}

	void exprTID(ParseArgs pa) {
		switch (lastAddedNKind(pa)) {
			case AST_ROOT: case AST_ID_OPER:
				break;
			case AST_ID_LEAF:
				break;

		}
	}

	void exprLeftParen(ParseArgs pa) {
		switch (lastAddedNKind(pa)) {
			case AST_ROOT: case AST_ID_OPER: {
				int lParSz = ph.skipLPar();
				assert lParSz > 0;
				AstProdSubTreeN prevSubTree = null;

				while (true) {
					AstProdSubTreeN subTree = exprs();
					lParSz -= ph.skipRPar();
					if (lParSz < 0) {
						throw new AssertionError("Unmatched Right Parentheses");
					}
					if(prevSubTree != null){
						assert subTree.lastAdded.isOperator();
						subTree.lastAdded.setAstRightN(prevSubTree);
					}
					prevSubTree = subTree;
					if(lParSz == 0) {
						assert pa.getLastAdded().isOperator();
						pa.getLastAdded().setAstRightN(prevSubTree);
						break;
					}
				}
				break;
			}
			default:
				throw new AssertionError("LParen in unexpected place");
		}
	}

	private AstNodKind lastAddedNKind(ParseArgs pa) {
		return pa.getLastAdded().astNKind();
	}
}
