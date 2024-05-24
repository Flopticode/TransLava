package helpers;

public abstract class Troublemaker
{
	public static final IllegalStateException howdWeEndUpHere()
	{
		return invalidState("How'd we end up here?");
	}
	public static final IllegalStateException invalidState(String msg)
	{
		return  new IllegalStateException(msg);
	}
}
