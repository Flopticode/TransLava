package statemachine;

import java.util.Optional;

import statemachine.AccessModifierState.InternalState;
import statemachine.model.AccessModifier;

public class AccessModifierState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Public(true),
		Protected(true),
		Private(true);
		
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
				new PrimitiveState("public"),
				new PrimitiveState("protected"),
				new PrimitiveState("private"),
			};
		}
	}
	
	private Optional<AccessModifier> accessModifier = Optional.empty();
	
	public AccessModifierState()
	{
		super(new InternalState[]{InternalState.Public, InternalState.Protected, InternalState.Private}, InternalState::createTranspilerStates);
	}

	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case Public:
			accessModifier = Optional.of(AccessModifier.PUBLIC);
			break;
		
		case Protected:
			accessModifier = Optional.of(AccessModifier.PROTECTED);
			break;
			
		case Private:
			accessModifier = Optional.of(AccessModifier.PRIVATE);
			break;
		}
	}

	@Override
	protected void reset()
	{
		accessModifier = Optional.empty();
	}
	
	public Optional<AccessModifier> getAccessModifier()
	{
		return accessModifier;
	}
}
