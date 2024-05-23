package statemachine;

import java.util.Optional;

import statemachine.model.IntegerValue;

public class IntegerValueState extends PrimitiveState
{
	public Optional<IntegerValue> getValue()
	{
		Optional<String> oInput = getInput();
		if(oInput.isPresent())
		{
			String input = oInput.get();
			try
			{
				return Optional.of(new IntegerValue(Integer.parseInt(input)));
			}
			catch(NumberFormatException nfe)
			{
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
}
