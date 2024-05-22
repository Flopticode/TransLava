package luawriter;

import java.util.Vector;

import statemachine.model.JavaFunction;
import statemachine.model.JavaInstruction;
import statemachine.model.JavaVariable;

public class LuaFunctionWriter
{
	public static String write(JavaFunction function)
	{
		String out = "";
		
		out += "function " + function.getName() + "(";
		
		Vector<JavaVariable> parameters = function.getParameters();
		for(int i = 0; i < parameters.size(); i++)
		{
			JavaVariable parameter = parameters.get(i);
			out += parameter.getName();
			
			if(i != parameters.size() - 1)
				out += ", ";
		}
		
		out += ")\n";
		
		for(JavaInstruction instruction : function.getInstructions())
		{
			out += LuaInstructionWriter.write(instruction);
		}
		
		out += "end\n";
		return out;
	}
}
