package statemachine;

import java.util.Vector;

import main.Tokenizer;

public class Transpiler
{
	@SuppressWarnings("serial")
	public static class ParsingException extends Exception
	{
		public ParsingException(String reason)
		{
			super(reason);
		}
	}
	
	public static void transpile(String content) throws ParsingException
	{
		Vector<String> tokens = Tokenizer.tokenize(content);
		
		TranspilerState<?> state = new JavaFileState();
		
		for(String token : tokens)
		{
			if(!state.canConsume(token))
				throwParsingError();
			
			state.evaluate(token);
		}
	}
	
	private static void throwParsingError() throws ParsingException
	{
		throw new ParsingException("Your code doesn't work.");
	}
}
