package statemachine.model;

public class IntegerValue extends JavaValue
{
	private int i;
	
	public IntegerValue(int i)
	{
		this.i = i;
	}
	
	public int getInteger()
	{
		return i;
	}
}
