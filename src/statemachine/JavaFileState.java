package statemachine;

import java.util.LinkedList;
import java.util.Optional;

import statemachine.JavaFileState.InternalState;
import statemachine.model.JavaClass;
import statemachine.model.JavaImport;

public class JavaFileState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		PackageDecl(false),
		ImportList(false),
		ClassDecl(true);
		
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
				new PackageDeclState(),
				new ImportListState(),
				new ClassDeclState()
			};
		}
	}
	
	private Optional<String> packageName = Optional.empty();
	private LinkedList<JavaImport> imports;
	private Optional<JavaClass> javaClass = Optional.empty();
	
	public JavaFileState()
	{
		super(new InternalState[]{InternalState.PackageDecl}, InternalState::createTranspilerStates);
	}
	
	public void onFinish(InternalState finished)
	{
		switch(finished)
		{
		case PackageDecl:
			packageName = ((PackageDeclState)getTranspilerState(InternalState.PackageDecl)).getPackageName();
			this.addActive(InternalState.ImportList);
			break;
			
		case ImportList:
			imports = ((ImportListState)getTranspilerState(InternalState.ImportList)).getImports();
			this.addActive(InternalState.ClassDecl);
			break;
			
		case ClassDecl:
			javaClass = ((ClassDeclState)getTranspilerState(InternalState.ClassDecl)).getJavaClass();
			break;
		}
	}
	
	public Optional<String> getPackageName()
	{
		return packageName;
	}
	public LinkedList<JavaImport> getImports()
	{
		return imports;
	}
	public Optional<JavaClass> getJavaClass()
	{
		return javaClass;
	}
	
	@Override
	public void reset()
	{
		packageName = Optional.empty();
		imports.clear();
		javaClass = Optional.empty();
	}
}
