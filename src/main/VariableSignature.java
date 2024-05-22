package main;

import statemachine.model.AccessModifier;

public class VariableSignature
{
	private String type;
	private AccessModifier access;
	private String name;
	
	public VariableSignature(AccessModifier access, String type, String name)
	{
		this.access = access;
		this.type = type;
		this.name = name;
	}
	
	public boolean equals(Object other)
	{
		if(other instanceof VariableSignature s)
		{
			return s.type.equals(this.type)
					&& s.access.equals(this.access)
					&& s.name.equals(this.name);
		}
		return false;
	}
}
