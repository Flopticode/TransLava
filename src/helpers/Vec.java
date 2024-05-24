package helpers;

import java.util.Vector;

public abstract class Vec
{
	@SafeVarargs
	public static final <T> Vector<T> of(T...ts)
	{
		Vector<T> tVec = new Vector<>();
		for(T t : ts)
			tVec.add(t);
		return tVec;
	}
	public static final <T> Vector<T> empty()
	{
		return Vec.of();
	}
}
