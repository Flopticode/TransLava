package statemachine.model;

public class JavaAssignmentInstruction extends JavaInstruction
{
	private String variableName;
	private JavaValue value;
	
	public JavaAssignmentInstruction(String variableName, JavaValue value)
	{
		this.variableName = variableName;
		this.value = value;
	}
	
	public String getVariableName()
	{
		return variableName;
	}
	public JavaValue getValue()
	{
		return value;
	}
}
