package statemachine;

import org.apache.commons.lang3.ArrayUtils;

public class IdentifierPrimitiveState extends AlphanumericPrimitiveState
{
	@Override
	public boolean canConsume(String token)
	{
		if(ArrayUtils.contains(KeywordPrimitiveState.KEYWORDS, token))
			return false;
		
		return super.canConsume(token);
	}
}
