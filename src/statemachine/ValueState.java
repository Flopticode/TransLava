package statemachine;

import java.util.Optional;
import java.util.Vector;

import helpers.Troublemaker;
import helpers.Vec;
import statemachine.ValueState.InternalState;
import statemachine.model.IntegerValue;
import statemachine.model.JavaValue;

public class ValueState extends TranspilerState<InternalState>
{
	protected static enum InternalState implements FinalStates
	{
		IntegerValue(true);
		
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
				new IntegerValueState()
			};
		}
	}
	
	private Optional<JavaValue> value;
	
	public ValueState()
	{
		super(new InternalState[]{InternalState.IntegerValue}, InternalState::createTranspilerStates);
	}

	@Override
	public Vector<InternalState> onFinish(InternalState state)
	{
		switch(state)
		{
		case IntegerValue:
			Optional<IntegerValue> oIValue = ((IntegerValueState)getTranspilerState(InternalState.IntegerValue)).getValue();
			if(oIValue.isPresent())
				value = Optional.of(oIValue.get());
			return Vec.empty();
		}
		
		throw Troublemaker.howdWeEndUpHere();
	}

	@Override
	protected void reset()
	{
		value = Optional.empty();
	}
	
	public Optional<JavaValue> getValue()
	{
		return value;
	}
}
