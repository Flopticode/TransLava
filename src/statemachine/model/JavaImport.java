package statemachine.model;

import java.util.regex.Pattern;

public class JavaImport
{
	private String[] packageSignature;
	
	public JavaImport(String packageSignature)
	{
		this.packageSignature = packageSignature.split(Pattern.quote("."));
	}
	
	public String[] getPackageSignature()
	{
		return packageSignature;
	}
	public String getClassName()
	{
		return packageSignature[packageSignature.length - 1];
	}
}
