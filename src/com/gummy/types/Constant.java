package com.gummy.types;

public class Constant {

	public static final Constant EOF = new Constant("#EOF");
	public static final Constant UNSPECIFIED = new Constant("");
	
	private String constant; 
	
	public Constant(String constant){
		this.constant = constant;
	}
	
	public String toString(){
		return this.constant;
	}
	
	
	
}
