package statemachine;

import java.util.Optional;

import statemachine.AssignmentInstructionState.InternalState;
import statemachine.model.JavaAssignmentInstruction;
import statemachine.model.JavaValue;

public class AssignmentInstructionState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Var(false),
		Equals(false),
		Value(false),
		Semicolon(true);
		
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
				new PrimitiveState(),
				new PrimitiveState("="),
				new ValueState(),
				new PrimitiveState(";")
			};
		}
	}
	
	private Optional<String> variableName = Optional.empty();
	private Optional<JavaValue> value = Optional.empty();
	
	public AssignmentInstructionState()
	{
		super(new InternalState[]{InternalState.Var}, InternalState::createTranspilerStates);
	}

	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case Var:
			variableName = ((PrimitiveState)getTranspilerState(InternalState.Var)).getInput();
			this.addActive(InternalState.Equals);
			break;
		
		case Equals:
			this.addActive(InternalState.Value);
			break;
		
		case Value:
			value = ((ValueState)getTranspilerState(InternalState.Value)).getValue();
			this.addActive(InternalState.Semicolon);
			break;
			
		case Semicolon:
			break;
		}
	}

	@Override
	protected void reset()
	{
		variableName = Optional.empty();
		value = Optional.empty();
	}
	
	public Optional<JavaAssignmentInstruction> getInstruction()
	{
		if(variableName.isPresent() && value.isPresent())
		{
			return Optional.of(new JavaAssignmentInstruction(variableName.get(), value.get()));
		}
		return Optional.empty();
	}
}
