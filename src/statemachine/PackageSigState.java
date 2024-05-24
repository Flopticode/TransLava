package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
import statemachine.PackageSigState.InternalState;

public class PackageSigState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Name(true),
		Dot(false);
		
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
				new IdentifierPrimitiveState(),
				new PrimitiveState(".")
			};
		}
	}
	
	private Optional<String> signature = Optional.of("");
	
	public PackageSigState()
	{
		super(new InternalState[]{InternalState.Name}, InternalState::createTranspilerStates);
	}

	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case Name:
			Optional<String> oInput = ((PrimitiveState)getTranspilerState(InternalState.Name)).getInput();
			if(oInput.isPresent())
				if(signature.isPresent())
					signature = Optional.of(signature.get() + oInput.get());
			
			return Vec.of(InternalState.Dot);
			
		case Dot:
			Optional<String> oInput2 = ((PrimitiveState)getTranspilerState(InternalState.Dot)).getInput();
			if(oInput2.isPresent())
				if(signature.isPresent())
					signature = Optional.of(signature.get() + oInput2.get());
			
			return Vec.of(InternalState.Name);
		}
		
		throw Troublemaker.howdWeEndUpHere();
	}

	@Override
	protected void reset()
	{
		signature = Optional.of("");
	}
	
	public Optional<String> getPackageSignature()
	{
		return signature;
	}
}
