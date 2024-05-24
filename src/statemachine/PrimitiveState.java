package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Vec;
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
	public PrimitiveState(boolean expectAlphanumeric)
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
	protected void resetStates()
	{
		receivedInput = Optional.empty();
	}
	
	@Override
	protected boolean consumesAny()
	{
		return !receivedInput.isPresent();
	}

	@Override
	public boolean canConsume(String token)
	{
		if(!consumesAny())
			return false;
		
		if(expectedInput.isPresent())
			return token.equals(expectedInput.get());
			
		return true;
	}
	
	@Override
	public boolean evaluate(String token)
	{
		if(canConsume(token))
		{
			receivedInput = Optional.of(token);
		}
		return receivedInput.isPresent();
	}
	
	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		return Vec.empty();
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
