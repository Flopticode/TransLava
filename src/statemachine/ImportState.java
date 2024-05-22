package statemachine;

import java.util.Optional;

import statemachine.model.JavaImport;

public class ImportState extends TranspilerState
{
	private Optional<JavaImport> javaImport = Optional.empty();
	
	public Optional<JavaImport> getJavaImport()
	{
		return javaImport;
	}
}
