package com.flint.compiler.frontend.parse.lex;

import com.flint.compiler.frontend.parse.lex.token.type.fToken;

public interface fLexer {
	fToken nextToken() ;
	fToken lookAhead(int n) ;
	boolean isEOF();
}
