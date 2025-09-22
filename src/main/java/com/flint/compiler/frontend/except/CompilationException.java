package com.flint.compiler.frontend.except;

public class CompilationException extends RuntimeException{
	public CompilationException(){}
	public CompilationException(String msg){
		super(msg);
	}
}
