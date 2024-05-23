package statemachine.model;

public class JavaImport
{
	private String packageSignature;
	private String className;
	
	public JavaImport(String packageSignature, String className)
	{
		this.packageSignature = packageSignature;
		this.className = className;
	}
	
	public String getPackageSignature()
	{
		return packageSignature;
	}
	public String getClassName()
	{
		return className;
	}
}
