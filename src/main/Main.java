package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import statemachine.JavaFileState;
import statemachine.model.JavaClass;
import statemachine.model.JavaImport;

public abstract class Main
{
	protected static transient volatile boolean foundTheLongestAttributeSignatureLol;
	protected static synchronized strictfp boolean foundTheLongestFunctionSignatureLol()
	{
		return false;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File("./tests/MinimalCode.java")));
		String content = "";
		while(br.ready())
			content += br.readLine() + "\n";
		br.close();
		
		var tokens = Tokenizer.tokenize(content);
		
		var state = new JavaFileState();
		System.out.println("Evaluating tokens...");
		int tokenIndex = 0;
		for(; tokenIndex < tokens.size(); tokenIndex++)
		{
			String token = tokens.get(tokenIndex);
			
			System.out.println(token);
			state.evaluate(token);
			
		}
		System.out.println("=== NO TOKENS LEFT TO CONSUME ===");
		
		if(tokenIndex < tokens.size())
		{
			System.err.println("Not all tokens could be consumed. Remaining tokens:");
			for(; tokenIndex < tokens.size(); tokenIndex++)
			{
				System.err.println(tokens.get(tokenIndex));
			}
			System.err.println("=== TOKENS DONE ===");
		}
		
		Optional<JavaClass> oJavaClass = state.getJavaClass();
		
		if(!oJavaClass.isPresent())
		{
			System.err.println("### Java class could not be created. ###");
		}
		else
		{
			JavaClass javaClass = oJavaClass.get();
			String classname = javaClass.getClassName();
			System.out.println("*** Java class named \"" + classname + "\" was created. ***");
		}
		
		if(state.getImports().size() > 0)
		{
			System.out.println("Our imports are: ");
			for(JavaImport javaImport : state.getImports())
			{
				String[] sig = javaImport.getPackageSignature();
				for(String part : sig)
				{
					System.out.print(part);
					if(!part.equals(sig[sig.length - 1]))
						System.out.print(".");
				}
				System.out.println();
			}
		}
		else
		{
			System.out.println("No imports found.");
		}
	}
}
