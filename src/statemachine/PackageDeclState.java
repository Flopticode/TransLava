package statemachine;

import java.util.Optional;

@SuppressWarnings("rawtypes")
public class PackageDeclState extends TranspilerState
{
	private Optional<String> packageName;
	
	public Optional<String> getPackageName()
	{
		return packageName;
	}
}
