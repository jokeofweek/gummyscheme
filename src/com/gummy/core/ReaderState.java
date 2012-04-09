package com.gummy.core;

/**
 * This state allows the {@link Reader}'s
 * {@link Reader#read(java.io.PushbackInputStream)} function to allow certain
 * tokens in certain states.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public enum ReaderState {
	NORMAL, QUOTE, QUASIQUOTE
}
