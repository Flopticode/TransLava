package statemachine;

import java.util.LinkedList;
import java.util.Optional;

import statemachine.ImportListState.InternalState;
import statemachine.model.JavaImport;

public class ImportListState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		ImportDecl(true);
		
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
				new ImportDeclState()
			};
		}
	}
	
	private LinkedList<JavaImport> imports = new LinkedList<>();
	
	public ImportListState()
	{
		super(new InternalState[]{InternalState.ImportDecl}, InternalState::createTranspilerStates);
	}

	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case ImportDecl:
			Optional<JavaImport> oImport = ((ImportDeclState)getTranspilerState(InternalState.ImportDecl)).getImport();
			if(oImport.isPresent())
				imports.add(oImport.get());
			this.addActive(InternalState.ImportDecl);
			break;
		}
	}

	@Override
	protected void reset()
	{
		imports.clear();
	}
	
	public LinkedList<JavaImport> getImports()
	{
		return imports;
	}
	
	
}
