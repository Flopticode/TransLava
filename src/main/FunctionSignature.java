package main;

import statemachine.model.AccessModifier;

public class FunctionSignature
{
	private AccessModifier access;
	private String returnType;
	private String name;
	private VariableSignature[] parameters;
	
	public FunctionSignature(AccessModifier access, String returnType, String name, VariableSignature... parameters)
	{
		this.access = access;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}
	
	public boolean equals(Object other)
	{
		if(other instanceof FunctionSignature s)
		{
			boolean plainCmp = s.access.equals(access)
					&& s.name.equals(name)
					&& s.returnType.equals(returnType)
					&& s.parameters.length == parameters.length;
			
			if(!plainCmp)
				return false;
			
			for(int i = 0; i < s.parameters.length; i++)
			{
				if(!parameters[i].equals(s.parameters[i]))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
