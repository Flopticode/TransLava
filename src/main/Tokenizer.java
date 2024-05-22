package main;

import java.util.Vector;

public class Tokenizer
{
	public static Vector<String> tokenize(String str)
	{
		Vector<String> tokens = new Vector<>();
		String curToken = "";
		
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			
			if(!(isLetter(c) || isNumber(c)))
				curToken = endToken(tokens, curToken);
			curToken += str.charAt(i);
		}
		return tokens;
	}
	
	private static String endToken(Vector<String> tokens, String curToken)
	{
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
