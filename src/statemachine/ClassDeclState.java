package statemachine;

import java.util.LinkedList;
import java.util.Optional;

import statemachine.ClassDeclState.InternalState;
import statemachine.model.AccessModifier;
import statemachine.model.JavaAttribute;
import statemachine.model.JavaClass;
import statemachine.model.JavaFunction;

public class ClassDeclState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		AccessModifier(false),
		HeadStatic(false),
		Class(false),
		Classname(false),
		Extends(false),
		SuperclassName(false),
		Implements(false),
		IFaceName(false),
		Comma(false),
		OpenCurlyBracket(false),
		BodyStatic(false),
		InstructionBody(false),
		AttributeDecl(false),
		FunctionDecl(false),
		ClassDecl(false),
		ConstructorDecl(false),
		ClosedCurlyBracket(true);
		
		private boolean isFinal;
		
		InternalState(boolean isFinal)
		{
			this.isFinal = isFinal;
		}

		@Override
		public boolean isFinal()
		{
			return isFinal;
		}
		
		@SuppressWarnings("unchecked")
		public static TranspilerState<InternalState>[] createTranspilerStates()
		{
			return new TranspilerState[] {
				new AccessModifierState(),
				new PrimitiveState("static"),
				new PrimitiveState("class"),
				new PrimitiveState(),
				new PrimitiveState("extends"),
				new PrimitiveState(),
				new PrimitiveState("implements"),
				new PrimitiveState(),
				new PrimitiveState(","),
				new PrimitiveState("{"),
				new PrimitiveState("static"),
				new InstructionBodyState(),
				new PrimitiveState("asdasdasd"),//new AttributeDeclState(),
				new PrimitiveState("asdasdasd"),//new FunctionDeclState(),
				new PrimitiveState("asdasdasd"),//new ClassDeclState(),
				new PrimitiveState("asdasdasd"),//new ConstructorDeclState(),
				new PrimitiveState("}")
			};
		}
	}
	
	private boolean isStatic = false;
	private Optional<AccessModifier> accessModifier = Optional.empty();
	private Optional<String> className = Optional.empty();
	private Optional<String> superClassName = Optional.empty();
	private LinkedList<String> implementedInterfaces = new LinkedList<>();
	private LinkedList<JavaFunction> functions = new LinkedList<>();
	private LinkedList<JavaAttribute> attributes = new LinkedList<>();
	
	public ClassDeclState()
	{
		super(  new InternalState[] {InternalState.AccessModifier, InternalState.HeadStatic, InternalState.Class},
				InternalState::createTranspilerStates);
	}
	
	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case AccessModifier:
			accessModifier = ((AccessModifierState)getTranspilerState(InternalState.AccessModifier)).getAccessModifier();
			this.addActive(InternalState.HeadStatic, InternalState.Class);
			break;
			
		case HeadStatic:
			isStatic = true;
			this.addActive(InternalState.Class);
			break;
			
		case Class:
			this.addActive(InternalState.Classname);
			break;
			
		case Classname:
			className = ((PrimitiveState)getTranspilerState(InternalState.Classname)).getInput();
			this.addActive(InternalState.Extends, InternalState.Implements, InternalState.OpenCurlyBracket);
			break;
			
		case Extends:
			this.addActive(InternalState.SuperclassName);
			break;
			
		case SuperclassName:
			superClassName = ((PrimitiveState)getTranspilerState(InternalState.SuperclassName)).getInput();
			this.addActive(InternalState.Implements, InternalState.OpenCurlyBracket);
			break;
			
		case Implements:
			this.addActive(InternalState.IFaceName);
			break;
			
		case IFaceName:
			var input = ((PrimitiveState)getTranspilerState(InternalState.SuperclassName)).getInput();
			if(input.isPresent())
				implementedInterfaces.add(input.get());
			this.addActive(InternalState.Comma);
			break;
			
		case Comma:
			this.addActive(InternalState.IFaceName);
			break;
			
		case OpenCurlyBracket:
			this.addActive(InternalState.ClosedCurlyBracket,
					InternalState.BodyStatic,
					InternalState.AttributeDecl,
					InternalState.FunctionDecl,
					InternalState.ClassDecl,
					InternalState.ConstructorDecl);
			break;
			
		case ClosedCurlyBracket:
			break;
			
		case BodyStatic:
			this.addActive(InternalState.InstructionBody);
			break;
		
		case InstructionBody:
			
		}
	}
	
	public Optional<JavaClass> getJavaClass()
	{
		if(!className.isPresent())
			return Optional.empty();
		return Optional.of(new JavaClass(isStatic, accessModifier, className.get(), superClassName, implementedInterfaces, functions, attributes));
	}
	

	@Override
	protected void reset()
	{
		accessModifier = Optional.empty();
		className = Optional.empty();
		superClassName = Optional.empty();
		implementedInterfaces.clear();
	}
}
