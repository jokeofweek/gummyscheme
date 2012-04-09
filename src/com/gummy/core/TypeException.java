package com.gummy.core;

public class TypeException extends RuntimeException {
	
	private static final long serialVersionUID = 1575732656035185819L;

	public TypeException(Object o, Class<?> expected){
		super("Expected " + expected.getName() + ", but recieved " + o.getClass().getName() + "(" + o + ")");
	}
	
}
