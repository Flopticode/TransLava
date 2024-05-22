package statemachine;

import java.util.Optional;

import statemachine.PrimitiveState.InternalState;

public class PrimitiveState extends TranspilerState<InternalState>
{
	protected static enum InternalState
	{
		
	}
	
	private Optional<String> expectedInput = Optional.empty();
	private Optional<String> receivedInput = Optional.empty();

	public PrimitiveState()
	{
		this(Optional.empty());
	}
	public PrimitiveState(String expectedInput)
	{
		this(Optional.of(expectedInput));
	}
	public PrimitiveState(Optional<String> expectedInput)
	{
		super(new InternalState[] {}, null);
		this.expectedInput = expectedInput;
	}

	@Override
	public boolean canConsume(String token)
	{
		if(expectedInput.isPresent())
			return token.equals(expectedInput.get());
		return true;
	}
	
	@Override
	public void evaluate(String token)
	{
		if(canConsume(token))
		{
			receivedInput = Optional.of(token);
		}
	}

	@Override
	public boolean isAccepting()
	{
		return receivedInput.isPresent();
	}
	
	@Override
	public void updateState()
	{
		
	}

	public Optional<String> getInput()
	{
		return expectedInput;
	}
}
