package statemachine.model;

import java.util.Optional;

public class JavaAttribute
{
	private boolean isStatic;
	private Optional<AccessModifier> accessModifier;
	private String name;
	private Optional<Object> defaultValue;
	
	public JavaAttribute(boolean isStatic, Optional<AccessModifier> accessModifier, String name, Optional<Object> defaultValue)
	{
		this.isStatic = isStatic;
		this.accessModifier = accessModifier;
		this.name = name;
		this.defaultValue = defaultValue;
	}
	
	public boolean isStatic()
	{
		return isStatic;
	}
	public Optional<AccessModifier> getAccessModifier()
	{
		return accessModifier;
	}
	public String getName()
	{
		return name;
	}
	public Optional<Object> getDefaultValue()
	{
		return defaultValue;
	}
}
