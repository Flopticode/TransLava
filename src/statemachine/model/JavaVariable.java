package statemachine.model;

public class JavaVariable
{
	private String datatype;
	private String name;
	
	public JavaVariable(String datatype, String name)
	{
		this.datatype = datatype;
		this.name = name;
	}
	
	public String getDatatype()
	{
		return datatype;
	}
	public String getName()
	{
		return name;
	}
}
