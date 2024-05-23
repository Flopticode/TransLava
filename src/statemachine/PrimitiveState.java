package statemachine;

import java.util.Optional;

import statemachine.PrimitiveState.InternalState;

public class PrimitiveState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		DEFAULT(true);
		
		private boolean isFinal;
		
		InternalState(boolean isFinal)
		{
			this.isFinal = isFinal;
		}

		@Override
		public boolean isFinal()
		{
			return isFinal;
		}
		
		@SuppressWarnings("unchecked")
		public static TranspilerState<InternalState>[] createTranspilerStates()
		{
			return new TranspilerState[] {
				null
			};
		}
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
		super(new InternalState[] {InternalState.DEFAULT}, InternalState::createTranspilerStates);
		this.expectedInput = expectedInput;
	}

	@Override
	public boolean canConsume(String token)
	{
		if(receivedInput.isPresent())
			return false;
		
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
	public void onFinish(InternalState state)
	{
		
	}
	
	@Override
	protected void reset()
	{
		expectedInput = Optional.empty();
		receivedInput = Optional.empty();
	}
	
	public Optional<String> getInput()
	{
		return receivedInput;
	}
}
