package statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Supplier;

public abstract class TranspilerState<InternalState extends Enum<InternalState>>
{	
	private Enum<InternalState> internalStateEnum;
	private Map<InternalState, TranspilerState<? extends Enum<InternalState>>> states = new HashMap<>();
	private Vector<InternalState> activeStates = new Vector<>();
	private Supplier<TranspilerState<? extends Enum<InternalState>>[]> transpilerStateSupplier;
	private InternalState[] defaultActive;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TranspilerState(InternalState[] active, Supplier<TranspilerState<? extends Enum<InternalState>>[]> transpilerStateSupplier)
	{
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
			if(!activeStates.contains(is))
			{
				activeStates.add(is);
			}
		}
	}
	
	public void evaluate(String token)
	{
		Vector<InternalState> remove = new Vector<>();
		for(InternalState active : activeStates)
		{
			TranspilerState<? extends Enum<?>> transpilerState = getTranspilerState(active);
			
			if(transpilerState.canConsume(token))
			{
				transpilerState.evaluate(token);
			}
			else
			{
				onFinish(active);
				resetStates();
				reset();
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
	 * A state is accepting if any final state is accepting and there are no active states, meaning the parsing of the state is done.
	 */
	public boolean isAccepting()
	{
		for(InternalState s : internalStateEnum.getDeclaringClass().getEnumConstants())
		{
			if(((FinalStates)s).isFinal() && getTranspilerState(s).isAccepting())
			{
				return activeStates.isEmpty();
			}
		}
		return false;
	}
	public abstract void onFinish(InternalState state);
	private void resetStates()
	{
		activeStates.clear();
		
		this.addActive(defaultActive);
		
		TranspilerState[] transpilerStates = transpilerStateSupplier.get();
		
		InternalState[] internalStates = internalStateEnum.getDeclaringClass().getEnumConstants();
		for(int i = 0; i < internalStates.length; i++)
			states.put(internalStates[i], transpilerStates[i]);
	}
	protected abstract void reset();
}
