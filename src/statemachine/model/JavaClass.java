package statemachine.model;

import java.util.LinkedList;
import java.util.Optional;

public class JavaClass
{
	private boolean isStatic;
	private Optional<AccessModifier> accessModifier;
	private String className;
	private Optional<String> superClassName;
	private LinkedList<String> implementedInterfaces;
	private LinkedList<JavaFunction> functions;
	private LinkedList<JavaAttribute> attributes;
	
 	public JavaClass(boolean isStatic, Optional<AccessModifier> accessModifier, String className,
 			Optional<String> superClassName, LinkedList<String> implementedInterfaces,
 			LinkedList<JavaFunction> functions, LinkedList<JavaAttribute> attributes)
	{
		this.isStatic = isStatic;
		this.accessModifier = accessModifier;
		this.className = className;
		this.superClassName = superClassName;
		this.implementedInterfaces = implementedInterfaces;
		this.functions = functions;
		this.attributes = attributes;
	}
	
	public boolean isStatic()
	{
		return isStatic;
	}
	public Optional<AccessModifier> getAccessModifier()
	{
		return accessModifier;
	}
	public String getClassName()
	{
		return className;
	}
	public Optional<String> getSuperClassName()
	{
		return superClassName;
	}
	public LinkedList<String> getImplementedInterfaces()
	{
		return implementedInterfaces;
	}
	public LinkedList<JavaFunction> getFunctions()
	{
		return functions;
	}
	public LinkedList<JavaAttribute> getAttributes()
	{
		return attributes;
	}
}
 