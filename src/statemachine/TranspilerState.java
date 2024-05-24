package statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Supplier;

public abstract class TranspilerState<InternalState extends Enum<InternalState>>
{	
	private InternalState[] internalStates;
	private Map<InternalState, TranspilerState<? extends Enum<InternalState>>> states = new HashMap<>();
	private Vector<InternalState> activeStates = new Vector<>();
	private Supplier<TranspilerState<? extends Enum<InternalState>>[]> transpilerStateSupplier;
	private InternalState[] defaultActive;
	
	public TranspilerState(InternalState[] active, Supplier<TranspilerState<? extends Enum<InternalState>>[]> transpilerStateSupplier)
	{
		this.internalStates = active[0].getDeclaringClass().getEnumConstants();
		this.transpilerStateSupplier = transpilerStateSupplier;
		this.defaultActive = active;
		resetStates(false);
	}
	
	protected TranspilerState<? extends Enum<?>> getTranspilerState(InternalState state)
	{
		return states.get(state);
	}
	
	public boolean evaluate(String token)
	{
		boolean accepting = false;
		
		Vector<InternalState> remove = new Vector<>();
		
		for(InternalState active : activeStates)
		{
			TranspilerState<? extends Enum<?>> activeSubState = getTranspilerState(active);
			
			if(activeSubState.canConsume(token))
			{
				System.err.println("Eval " + active);
				
				if(activeSubState.evaluate(token))
				{
					activeStates = onFinish(active);
					if(activeSubState.consumesAny())
						activeStates.add(active);
					
					if(((FinalStates)active).isFinal())
						accepting = true;
					
					System.err.println("Resetting " + active + " (for second use)");
					if(!activeSubState.consumesAny())
						activeSubState.resetStates();
					
					
					System.err.print("Set ");
					for(InternalState na : activeStates)
						System.err.print(na + ", ");
					System.err.println("as branch");
					
				}
			}
			else
			{
				remove.add(active);
				System.err.println("Removed " + active + " as branch. (won't consume " + token + ")");
			}
		}
		
		for(InternalState r : remove)
			activeStates.remove(r);
		
		System.err.println("..");
		
		return accepting;
	}
	
	protected boolean consumesAny()
	{
		for(InternalState na : activeStates)
		{
			if(getTranspilerState(na).consumesAny())
				return true;
		}
		return false;
	}
	
	public boolean canConsume(String token)
	{
		for(InternalState active : activeStates)
		{
			if(getTranspilerState(active).canConsume(token))
				return true;
		}
		return false;
	}	
	
	public abstract Vector<InternalState> onFinish(InternalState state);
	
	@SuppressWarnings("unchecked")
	private void resetStates(boolean doReset)
	{
		if(internalStates.length > 0)
		{
			activeStates.clear();
			
			TranspilerState<InternalState>[] transpilerStates = (TranspilerState<InternalState>[]) transpilerStateSupplier.get();
			
			for(int i = 0; i < internalStates.length; i++)
				states.put(internalStates[i], transpilerStates[i]);
			
			for(InternalState defaultState : defaultActive)
			{
				activeStates.add(defaultState);
			}
			
			if(doReset)
				reset();
		}
	}
	
	protected void resetStates()
	{
		resetStates(true);
	}
	protected abstract void reset();
}
