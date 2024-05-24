package statemachine;

import org.apache.commons.lang3.ArrayUtils;

public class KeywordPrimitiveState extends AlphanumericPrimitiveState
{
	public static final String[] KEYWORDS = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
			"class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
			"for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new",
			"package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};
	
	@Override
	public boolean canConsume(String token)
	{
		if(!ArrayUtils.contains(KEYWORDS, token))
			return false;
		
		return super.canConsume(token);
	}
}
