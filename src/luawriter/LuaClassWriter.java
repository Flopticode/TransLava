package luawriter;

import java.util.Optional;
import java.util.function.Function;

import statemachine.model.JavaAttribute;
import statemachine.model.JavaClass;
import statemachine.model.JavaFunction;

public class LuaClassWriter
{
	public static String write(boolean staticInstance, JavaClass jc, Function<String, JavaClass> classGetter)
	{
		String out = "";
		
		// Recursively copy the code of all super classes to mimic inheritance
		Optional<String> oSuperClassName = jc.getSuperClassName();
		if(oSuperClassName.isPresent())
		{
			JavaClass superClass = classGetter.apply(oSuperClassName.get());
			out += LuaClassWriter.write(staticInstance, superClass, classGetter);
		}
		
		// Add all the attributes
		for(JavaAttribute attribute : jc.getAttributes())
		{
			if(attribute.isStatic() == staticInstance)
				out += LuaAttributeWriter.write(attribute);
		}
		
		// Add all the functions
		for(JavaFunction function : jc.getFunctions())
		{
			if(function.isStatic() == staticInstance)
				out += LuaFunctionWriter.write(function);
		}
		
		return out;
	}
	public static String writeStaticInstance(JavaClass jc, Function<String, JavaClass> classGetter)
	{
		return write(true, jc, classGetter);
	}
	public static String writeDynamicInstance(JavaClass jc, Function<String, JavaClass> classGetter)
	{
		return write(false, jc, classGetter);
	}
}
