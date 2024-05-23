package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import statemachine.JavaFileState;
import statemachine.model.JavaClass;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File("./tests/MinimalCode.java")));
		String content = "";
		while(br.ready())
			content += br.readLine() + "\n";
		br.close();
		
		var tokens = Tokenizer.tokenize(content);
		
		var state = new JavaFileState();
		for(String token : tokens)
		{
			state.evaluate(token);
		}
		
		Optional<JavaClass> oJavaClass = state.getJavaClass();
		if(oJavaClass.isPresent())
		{
			System.out.println("We have a java class named \"" + oJavaClass.get().getClassName() + "\"!");
		}
		else
		{
			System.err.println("Nooo, where is my JavaClass :c");
			System.err.println("Tokens:");
			for(String token : tokens)
			{
				System.err.println(token);
			}
		}
	}
}
