package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
import statemachine.InstructionState.InternalState;
import statemachine.model.JavaAssignmentInstruction;
import statemachine.model.JavaInstruction;

public class InstructionState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Assignment(true);
		
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
				new AssignmentInstructionState()
			};
		}
	}
	
	private Optional<JavaInstruction> instruction = Optional.empty();
	
	public InstructionState()
	{
		super(new InternalState[]{InternalState.Assignment}, InternalState::createTranspilerStates);
	}
	
	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case Assignment:
			Optional<JavaAssignmentInstruction> oAssIns = ((AssignmentInstructionState)getTranspilerState(InternalState.Assignment)).getInstruction();
			if(oAssIns.isPresent())
				instruction = Optional.of(oAssIns.get());
			return Vec.empty();
		}
		
		throw Troublemaker.howdWeEndUpHere();
	}

	@Override
	protected void reset()
	{
		instruction = Optional.empty();
	}
	
	public Optional<JavaInstruction> getInstruction()
	{
		return instruction;
	}
}
