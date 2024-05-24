package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
import statemachine.PackageDeclState.InternalState;

public class PackageDeclState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Package(false),
		Name(false),
		Semicolon(true);
		
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
				new PrimitiveState("package"),
				new IdentifierPrimitiveState(),
				new PrimitiveState(";")
			};
		}
	}
	
	private Optional<String> packageName = Optional.empty();
	
	public PackageDeclState()
	{
		super(new InternalState[]{InternalState.Package}, InternalState::createTranspilerStates);
	}

	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case Package:
			return Vec.of(InternalState.Name);
		case Name:
			this.packageName = ((PrimitiveState)getTranspilerState(InternalState.Name)).getInput();
			return Vec.of(InternalState.Semicolon);
		case Semicolon:
			return Vec.empty();
		}
		
		throw Troublemaker.howdWeEndUpHere();
	}

	@Override
	protected void reset()
	{
		packageName = Optional.empty();
	}
	
	public Optional<String> getPackageName()
	{
		return packageName;
	}
}
