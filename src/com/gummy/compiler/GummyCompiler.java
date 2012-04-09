package com.gummy.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PushbackInputStream;

import com.gummy.core.Compiler;

public class GummyCompiler {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid usage: \nGummyCompiler file");
		}

		File file = new File(args[0]);

		// Try to open the file.
		PushbackInputStream in = null;
		try {
			in = new PushbackInputStream(new FileInputStream(file));
		} catch (IOException io) {
			System.out
					.println("Could not open the file passed as an argument.");

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			
			System.exit(1);
		}

		// Build the name of the gum file
		String newFileName = file.getName();
		int sepPos = newFileName.lastIndexOf('.');
		if (sepPos != -1) {
			newFileName = newFileName.substring(0, sepPos) + ".gum";
		} else {
			newFileName = newFileName + ".gum";
		}

		String path = file.getAbsolutePath();
		sepPos = path.lastIndexOf(System.getProperty("file.separator"));

		newFileName = path.substring(0, sepPos + 1) + newFileName;

		// Try to compile the file.
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(new FileOutputStream(newFileName));
			Compiler.compile(in, out);
		} catch (IOException io) {
			System.out
					.println("Could not compile the gum file.\nError Message:"
							+ io.getMessage());
			io.printStackTrace(System.out);
			System.exit(1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
