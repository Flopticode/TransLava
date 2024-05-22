package statemachine.model;

import java.util.Optional;
import java.util.Vector;

public class JavaFunction
{
	private boolean isStatic;
	private Optional<AccessModifier> accessModifier;
	private String name;
	private String returnType;
	private Vector<JavaInstruction> instructions;
	private Vector<JavaVariable> parameters;
	
	public JavaFunction(boolean isStatic, Optional<AccessModifier> accessModifier, String name, String returnType, Vector<JavaInstruction> instructions,
			Vector<JavaVariable> parameters)
	{
		this.isStatic = isStatic;
		this.accessModifier = accessModifier;
		this.name = name;
		this.returnType = returnType;
		this.instructions = instructions;
		this.parameters = parameters;
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
	public String getReturnType()
	{
		return returnType;
	}
	public Vector<JavaInstruction> getInstructions()
	{
		return instructions;
	}
	public Vector<JavaVariable> getParameters()
	{
		return parameters;
	}
}
