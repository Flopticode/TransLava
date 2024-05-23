package statemachine;

import java.util.LinkedList;

import statemachine.InstructionBodyState.InternalState;
import statemachine.model.JavaInstruction;

public class InstructionBodyState extends TranspilerState<InternalState>
{

	protected static enum InternalState implements FinalStates
	{
		OpenCurlyBracket(false),
		InstructionList(false),
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
				new PrimitiveState("{"),
				new InstructionListState(),
				new PrimitiveState("}")
			};
		}
	}
	
	private LinkedList<JavaInstruction> instructionList = new LinkedList<>();
	
	public InstructionBodyState()
	{
		super(new InternalState[]{InternalState.OpenCurlyBracket}, InternalState::createTranspilerStates);
	}

	@Override
	public void onFinish(InternalState state)
	{
		switch(state)
		{
		case OpenCurlyBracket:
			this.addActive(InternalState.InstructionList);
			break;
		
		case InstructionList:
			instructionList = ((InstructionListState)getTranspilerState(InternalState.InstructionList)).getInstructionList();
			this.addActive(InternalState.ClosedCurlyBracket);
			break;
		}
	}

	@Override
	protected void reset()
	{
		instructionList.clear();
	}
	
	public LinkedList<JavaInstruction> getInstructionList()
	{
		return instructionList;
	}
}
