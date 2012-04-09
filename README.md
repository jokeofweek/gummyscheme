GummyScheme
===

A Java Scheme interpreter based on Brian D. Carlstrom's [paper on `Embedding Scheme in Java'](http://carlstrom.com/publications/meng_thesis.pdf).

The interpreter supports both a REPL and compiling (to a .gum file). Use the GummyCompiler class to compile a file to a .gum file (by passing the filename as a command line argument) and then it can be re-read using the REPL class by passing the .gum filename as a command line argument.

Written by Dominic Charley-Roy