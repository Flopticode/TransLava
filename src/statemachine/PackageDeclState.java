package statemachine;

import java.util.Optional;

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
				new PrimitiveState(),
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
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case Package:
			this.addActive(InternalState.Name);
			break;
		case Name:
			this.packageName = ((PrimitiveState)getTranspilerState(InternalState.Name)).getInput();
			this.addActive(InternalState.Semicolon);
			break;
		case Semicolon:
			break;
		}
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
