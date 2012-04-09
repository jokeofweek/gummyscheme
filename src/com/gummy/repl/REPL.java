package com.gummy.repl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PushbackInputStream;

import com.gummy.core.Environment;
import com.gummy.core.Loader;

public class REPL {

	private Environment global;
	private PrintWriter out;
	
	public REPL(){
		global = new Environment();
		//Environment.initialize(global);
		out = new PrintWriter(System.out);
	}
	
	public void run() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
		while (true){
			System.out.print("> ");
			Loader.load(new PushbackInputStream(new ByteArrayInputStream(
					reader.readLine().getBytes())), out, global);
		}
	}
	
	/**
	 * Create a new REPL and run it.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new REPL().run();
	}

}
