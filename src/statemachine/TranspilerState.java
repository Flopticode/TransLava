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
		resetStates();
	}
	
	protected TranspilerState<? extends Enum<?>> getTranspilerState(InternalState state)
	{
		return states.get(state);
	}
	
	@SuppressWarnings("unchecked")
	protected void addActive(InternalState... states)
	{	
		for(InternalState is : states)
		{
			activeStates.add(is);
		}
	}
	
	public void evaluate(String token)
	{
		Vector<InternalState> remove = new Vector<>();
		
		int numActiveBefore = activeStates.size();
		for(int i = 0; i < numActiveBefore; i++)
		{
			InternalState active = activeStates.get(i);
			
			TranspilerState<? extends Enum<?>> transpilerState = getTranspilerState(active);
			
			if(transpilerState.canConsume(token))
			{
				transpilerState.evaluate(token);
				
				if(transpilerState.isAccepting())
				{
					onFinish(active);
				}
			}
			else
			{
				remove.add(active);
			}
		}
		
		for(InternalState r : remove)
		{
			activeStates.remove(r);
		}
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
	
	public boolean isAccepting(InternalState state)
	{
		return getTranspilerState(state).isAccepting();
	}
	
	/**
	 * A state is accepting if any final state is accepting
	 */
	public boolean isAccepting()
	{
		for(InternalState s : internalStates)
		{
			if(((FinalStates)s).isFinal() && getTranspilerState(s).isAccepting())
			{
				return true;
			}
		}
		return false;
	}
	public abstract void onFinish(InternalState state);
	
	@SuppressWarnings("unchecked")
	protected void resetStates()
	{
		if(internalStates.length > 0)
		{
			activeStates.clear();
			
			TranspilerState<InternalState>[] transpilerStates = (TranspilerState<InternalState>[]) transpilerStateSupplier.get();
			
			for(int i = 0; i < internalStates.length; i++)
				states.put(internalStates[i], transpilerStates[i]);
			
			this.addActive(defaultActive);
		}
	}
	protected abstract void reset();
}
