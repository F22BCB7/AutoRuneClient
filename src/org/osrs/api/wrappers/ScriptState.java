package org.osrs.api.wrappers;

public interface ScriptState{
	public int[] localInts();
	public int stackIndex();
	public String[] localStrings();
	public RuneScript invokedFromScript();
}