package statemachine;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
import statemachine.InstructionListState.InternalState;
import statemachine.model.JavaInstruction;

public class InstructionListState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		Instruction(true);
		
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
				new InstructionState()
			};
		}
	}
	
	private LinkedList<JavaInstruction> instructions = new LinkedList<>();
	
	public InstructionListState()
	{
		super(new InternalState[]{InternalState.Instruction}, InternalState::createTranspilerStates);
	}

	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case Instruction:
			Optional<JavaInstruction> oInstruction = ((InstructionState)getTranspilerState(InternalState.Instruction)).getInstruction();
			if(oInstruction.isPresent())
				instructions.add(oInstruction.get());
			return Vec.of(InternalState.Instruction);
		}
		
		throw Troublemaker.howdWeEndUpHere();
	}

	@Override
	protected void reset()
	{
		instructions.clear();
	}
	
	public LinkedList<JavaInstruction> getInstructionList()
	{
		return instructions;
	}
}
