aspect LoggingAspect {
    // Pointcut for all methods in fParser class
    pointcut fParserMethods() : execution(* com.flint.compiler.frontend.parse.fParser.*(..));

    before() : fParserMethods() {
        System.out.println("-----------> " + thisJoinPoint);
    }

    after() : fParserMethods() {
        System.out.println("<----------- " + thisJoinPoint);
    }
}
