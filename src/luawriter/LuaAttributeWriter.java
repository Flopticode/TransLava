package luawriter;

import statemachine.model.JavaAttribute;

public class LuaAttributeWriter
{
	public static String write(JavaAttribute ja)
	{
		var oDefaultValue = ja.getDefaultValue();
		if(!oDefaultValue.isPresent())
			return "";
		return ja.getName() + " = " + oDefaultValue.toString() + "\n";
	}
}
