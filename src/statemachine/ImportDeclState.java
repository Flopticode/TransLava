package statemachine;

import java.util.Optional;

import statemachine.ImportDeclState.InternalState;
import statemachine.model.JavaImport;

public class ImportDeclState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Import(false),
		PackageSig(false),
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
				new PrimitiveState("import"),
				new PackageSigState(),
				new PrimitiveState(";")
			};
		}
	}
	
	private Optional<String> packageSignature = Optional.empty();
	
	public ImportDeclState()
	{
		super(new InternalState[]{InternalState.Import}, InternalState::createTranspilerStates);
	}

	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case Import:
			this.addActive(InternalState.PackageSig);
			break;
			
		case PackageSig:
			packageSignature = ((PackageSigState)getTranspilerState(InternalState.PackageSig)).getPackageSignature();
			this.addActive(InternalState.Semicolon);
			break;
			
		case Semicolon:
			break;
		}
	}

	@Override
	protected void reset()
	{
		
	}
	
	public Optional<JavaImport> getImport()
	{
		if(packageSignature.isPresent())
		{
			return Optional.of(new JavaImport(packageSignature.get()));
		}
		return Optional.empty();
	}
}
