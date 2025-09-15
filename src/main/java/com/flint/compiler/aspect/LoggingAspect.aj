aspect LoggingAspect {
    // Pointcut for all methods in fParser class
    pointcut fParserMethods() : execution(* com.flint.compiler.frontend.parse.fParser.*(..));
    // Pointcut for all methods in token class
     pointcut tokenMethods() : execution(* com.flint.compiler.frontend.parse.lex.token.*(..));

    before() : fParserMethods() {
        System.out.println("-----------> " + thisJoinPoint);
    }

    before() : tokenMethods() {
        System.out.println("-----------> " + thisJoinPoint);
    }
}
