package statemachine;

import org.apache.commons.lang3.StringUtils;

public class AlphanumericPrimitiveState extends PrimitiveState
{
	public AlphanumericPrimitiveState()
	{
		super(true);
	}
	
	@Override
	public boolean canConsume(String token)
	{
		if(!StringUtils.isAlphanumeric(token))
		{
			return false;
		}
		
		return super.canConsume(token);
	}
}
