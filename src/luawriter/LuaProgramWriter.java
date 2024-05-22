package luawriter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import statemachine.model.JavaClass;

public class LuaProgramWriter
{
	private static Function<String, JavaClass> createClassGetter(JavaClass[] javaClasses)
	{
		return (String className) -> {
			for(JavaClass jc : javaClasses)
			{
				if(jc.getClassName().equals(className))
					return jc;
			}
			return null;
		};
	}
	
	public static Map<String, String> write(JavaClass[] javaClasses)
	{
		Map<String, String> luaFiles = new HashMap<>();
		
		var classGetter = createClassGetter(javaClasses);
		
		for(JavaClass javaClass : javaClasses)
		{
			luaFiles.put(javaClass.getClassName() + "STATICINSTANCE", LuaClassWriter.writeStaticInstance(javaClass, classGetter));
			luaFiles.put(javaClass.getClassName(), LuaClassWriter.writeDynamicInstance(javaClass, classGetter));
		}
		
		return luaFiles;
	}
}
