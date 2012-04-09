package com.gummy.repl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PushbackInputStream;

import com.gummy.core.Environment;
import com.gummy.core.Loader;

public class REPL {

	private Environment global;

	public REPL() {
		global = new Environment();
		Environment.initialize(global);
	}

	/**
	 * Starts a REPL.
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		while (true) {
			System.out.print("> ");
			Loader.load(new PushbackInputStream(new ByteArrayInputStream(reader
					.readLine().getBytes())), System.out, global);
		}
	}

	/**
	 * Runs a .gum file.
	 * 
	 * @param file
	 */
	public void runFile(String file) {
		ObjectInputStream in = null;
		// Open the input stream
		try {
			in = new ObjectInputStream(new FileInputStream(file));
		} catch (IOException ioEx) {
			System.out.println("Could not read .gum file.\nError message: "
					+ ioEx.getMessage());
			ioEx.printStackTrace(System.out);

			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
				}
			}

			System.exit(1);
		}

		// Try to run the compiled file
		try {
			Loader.loadCompiled(in, System.out, global);
		} catch (IOException e) {
			System.out.println("Error reading .gum file.\nError message: "
					+ e.getMessage());
			e.printStackTrace(System.out);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not read .gum file.\nError message: "
					+ e.getMessage());
			e.printStackTrace(System.out);
			System.exit(1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	/**
	 * Create a new REPL and run it.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			new REPL().run();
		} else {
			new REPL().runFile(args[0]);
		}
	}

}
