package main;

import java.util.Vector;

public class Tokenizer
{	
	public static Vector<String> tokenize(String str)
	{
		if(str.contains(" "))
		{
			String[] parts = str.split(" ");
			Vector<String> tokens = new Vector<>();
			for(String part : parts)
				tokens.addAll(tokenize(part));
			return tokens;
		}
		if(str.contains("\n"))
		{
			String[] parts = str.split("\n");
			Vector<String> tokens = new Vector<>();
			for(String part : parts)
				tokens.addAll(tokenize(part));
			return tokens;
		}
		
		Vector<String> tokens = new Vector<>();
		String curToken = "";
		
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			
			if(!(isLetter(c) || isNumber(c)))
			{
				curToken = endToken(tokens, curToken);
			}
			
			curToken += str.charAt(i);
		}
		if(!curToken.isEmpty())
			tokens.add(curToken);
		return tokens;
	}
	
	private static String endToken(Vector<String> tokens, String curToken)
	{
		if(!curToken.isEmpty())
			tokens.add(curToken);
		return "";
	}
	private static boolean isLetter(char c)
	{
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}
	private static boolean isNumber(char c)
	{
		return c >= '0' && c <= '9';
	}
	private static boolean isWhitespace(char c)
	{
		return c == '\n' || c == '\r' || c == ' ';
	}
}
