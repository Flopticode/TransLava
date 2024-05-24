package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
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
				new IdentifierPrimitiveState(),
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
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case Var:
			variableName = ((PrimitiveState)getTranspilerState(InternalState.Var)).getInput();
			return Vec.of(InternalState.Equals);
		
		case Equals:
			return Vec.of(InternalState.Value);
		
		case Value:
			value = ((ValueState)getTranspilerState(InternalState.Value)).getValue();
			return Vec.of(InternalState.Semicolon);
			
		case Semicolon:
			return Vec.empty();
		}
		
		throw Troublemaker.howdWeEndUpHere();
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
