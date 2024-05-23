package main;

import java.util.Vector;

public class Tokenizer
{
	private static final char[] whitespaces = {'\r', '\n', ' ', '\t'};
	
	public static Vector<String> tokenize(String str)
	{
		for(char whitespace : whitespaces)
		{
			if(str.contains("" + whitespace))
			{
				String[] parts = str.split("" + whitespace);
				Vector<String> tokens = new Vector<>();
				for(String part : parts)
					tokens.addAll(tokenize(part));
				return tokens;
			}
		}
		
		Vector<String> tokens = new Vector<>();
		String curToken = "";
		
		boolean prevNotAlphanumerical = false;
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			
			if(!(isLetter(c) || isNumber(c)))
			{
				curToken = endToken(tokens, curToken);
				prevNotAlphanumerical = true;
			}
			else if(prevNotAlphanumerical)
			{
				curToken = endToken(tokens, curToken);
				prevNotAlphanumerical = false;
			}
			
			curToken += c;
			
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
}
