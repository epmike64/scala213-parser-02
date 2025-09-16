aspect LoggingAspect {
    // Pointcut for all methods in fParser class
    pointcut fParserMethods() : execution(* com.flint.compiler.frontend.parse.fParser.*(..));
    // Pointcut for all methods in token class
     pointcut tokenMethods() : execution(* com.flint.compiler.frontend.parse.lex.token.*(..));

    // Pointcut for insertOperator method in ParseHelp class
     pointcut insertOperatorMethod() : execution(* com.flint.compiler.frontend.parse.ParseHelp.insertOperator(..));

    before() : fParserMethods() {
        System.out.println("   ---> " + thisJoinPoint.toString().replace("com.flint.compiler.frontend.", ""));
    }


    before() : tokenMethods() {
        System.out.println("   ---> " + thisJoinPoint.toString().replace("com.flint.compiler.frontend.", ""));
    }

    before() : insertOperatorMethod() {
            System.out.println("  ***-> " + thisJoinPoint.toString().replace("com.flint.compiler.frontend.", ""));
        }
}
